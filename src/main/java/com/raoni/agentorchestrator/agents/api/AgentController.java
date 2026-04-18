package com.raoni.agentorchestrator.agents.api;

import com.raoni.agentorchestrator.agents.application.AgentOrchestrationService;
import com.raoni.agentorchestrator.agents.dto.AgentTaskRequest;
import com.raoni.agentorchestrator.agents.dto.AgentTaskResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/agents/tasks")
public class AgentController {
    private final AgentOrchestrationService service;

    public AgentController(AgentOrchestrationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgentTaskResponse create(@Valid @RequestBody AgentTaskRequest request) {
        return service.execute(request);
    }

    @GetMapping("/{taskId}")
    public AgentTaskResponse get(@PathVariable UUID taskId) {
        return service.find(taskId);
    }
}
