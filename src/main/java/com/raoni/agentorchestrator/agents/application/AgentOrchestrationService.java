package com.raoni.agentorchestrator.agents.application;

import com.raoni.agentorchestrator.agents.domain.TaskStatus;
import com.raoni.agentorchestrator.agents.dto.AgentTaskRequest;
import com.raoni.agentorchestrator.agents.dto.AgentTaskResponse;
import com.raoni.agentorchestrator.agents.dto.ExecutionStep;
import com.raoni.agentorchestrator.audit.application.AuditService;
import com.raoni.agentorchestrator.messaging.application.DomainEventPublisher;
import com.raoni.agentorchestrator.planning.application.AgentPlanner;
import com.raoni.agentorchestrator.safety.application.PromptInjectionPolicy;
import com.raoni.agentorchestrator.tools.application.ToolRegistry;
import com.raoni.agentorchestrator.tools.domain.ToolResult;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AgentOrchestrationService {
    private final PromptInjectionPolicy promptInjectionPolicy;
    private final AgentPlanner planner;
    private final ToolRegistry toolRegistry;
    private final AgentTaskStore taskStore;
    private final AuditService auditService;
    private final DomainEventPublisher eventPublisher;

    public AgentOrchestrationService(
            PromptInjectionPolicy promptInjectionPolicy,
            AgentPlanner planner,
            ToolRegistry toolRegistry,
            AgentTaskStore taskStore,
            AuditService auditService,
            DomainEventPublisher eventPublisher
    ) {
        this.promptInjectionPolicy = promptInjectionPolicy;
        this.planner = planner;
        this.toolRegistry = toolRegistry;
        this.taskStore = taskStore;
        this.auditService = auditService;
        this.eventPublisher = eventPublisher;
    }

    public AgentTaskResponse execute(AgentTaskRequest request) {
        UUID taskId = UUID.randomUUID();
        var safety = promptInjectionPolicy.evaluate(request.goal(), request.context());

        if (!safety.allowed()) {
            AgentTaskResponse blocked = new AgentTaskResponse(
                    taskId,
                    TaskStatus.BLOCKED,
                    "Task blocked by AI safety policy.",
                    safety.warnings(),
                    List.of(),
                    Instant.now()
            );
            taskStore.save(blocked);
            auditService.record("AGENT_TASK_BLOCKED", taskId.toString());
            eventPublisher.publish("agent.task.blocked", taskId.toString());
            return blocked;
        }

        List<String> plan = planner.plan(request);
        List<ExecutionStep> trace = new ArrayList<>();
        int order = 1;
        for (String toolName : plan) {
            var tool = toolRegistry.find(toolName);
            if (tool.isEmpty()) {
                trace.add(new ExecutionStep(order++, toolName, "SKIPPED", request.goal(), "Tool not registered"));
                continue;
            }
            ToolResult result = tool.get().execute(request.context());
            trace.add(new ExecutionStep(order++, toolName, result.success() ? "SUCCESS" : "FAILED", request.goal(), result.summary()));
        }

        TaskStatus status = request.requireHumanApproval() ? TaskStatus.WAITING_FOR_APPROVAL : TaskStatus.COMPLETED;
        String summary = status == TaskStatus.WAITING_FOR_APPROVAL()
                ? "Agent prepared the workflow and is waiting for human approval before risky external actions."
                : "Agent completed the workflow with deterministic local tools.";

        AgentTaskResponse response = new AgentTaskResponse(taskId, status, summary, List.of(), trace, Instant.now());
        taskStore.save(response);
        auditService.record("AGENT_TASK_EXECUTED", taskId.toString());
        eventPublisher.publish("agent.task.executed", taskId.toString());
        return response;
    }

    public AgentTaskResponse find(UUID taskId) {
        return taskStore.find(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));
    }
}
