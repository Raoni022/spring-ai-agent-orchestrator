package com.raoni.agentorchestrator.agents.dto;

public record ExecutionStep(
        int order,
        String toolName,
        String status,
        String inputSummary,
        String outputSummary
) {
}
