package com.raoni.agentorchestrator.agents.application;

import com.raoni.agentorchestrator.agents.domain.TaskStatus;
import com.raoni.agentorchestrator.agents.dto.AgentTaskRequest;
import com.raoni.agentorchestrator.audit.application.AuditService;
import com.raoni.agentorchestrator.messaging.application.DomainEventPublisher;
import com.raoni.agentorchestrator.planning.application.AgentPlanner;
import com.raoni.agentorchestrator.safety.application.PromptInjectionPolicy;
import com.raoni.agentorchestrator.tools.application.CalendarProposeSlotsTool;
import com.raoni.agentorchestrator.tools.application.CrmLookupTool;
import com.raoni.agentorchestrator.tools.application.NotificationDraftEmailTool;
import com.raoni.agentorchestrator.tools.application.ToolRegistry;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AgentOrchestrationServiceTest {
    @Test
    void executesSafeAgentTaskWithTrace() {
        var registry = new ToolRegistry(List.of(new CrmLookupTool(), new CalendarProposeSlotsTool(), new NotificationDraftEmailTool()));
        var service = new AgentOrchestrationService(
                new PromptInjectionPolicy(),
                new AgentPlanner(),
                registry,
                new AgentTaskStore(),
                new AuditService(),
                new DomainEventPublisher()
        );

        var response = service.execute(new AgentTaskRequest(
                "Qualify lead and draft follow-up",
                "Lead asked for automation pricing",
                List.of("crm.lookup", "calendar.propose_slots", "notification.draft_email"),
                true
        ));

        assertThat(response.status()).isEqualTo(TaskStatus.WAITING_FOR_APPROVAL);
        assertThat(response.executionTrace()).hasSize(3);
    }
}
