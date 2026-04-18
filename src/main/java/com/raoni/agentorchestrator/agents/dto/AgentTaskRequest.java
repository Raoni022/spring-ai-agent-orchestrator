package com.raoni.agentorchestrator.agents.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AgentTaskRequest(
        @NotBlank String goal,
        @NotBlank String context,
        @NotEmpty List<String> allowedTools,
        boolean requireHumanApproval
) {
}
