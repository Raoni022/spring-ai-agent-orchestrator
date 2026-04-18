package com.raoni.agentorchestrator.agents.dto;

import com.raoni.agentorchestrator.agents.domain.TaskStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AgentTaskResponse(
        UUID taskId,
        TaskStatus status,
        String decisionSummary,
        List<String> safetyWarnings,
        List<ExecutionStep> executionTrace,
        Instant createdAt
) {
}
