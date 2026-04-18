package com.raoni.agentorchestrator.safety.dto;

import java.util.List;

public record SafetyDecision(boolean allowed, boolean requiresApproval, List<String> warnings) {
    public static SafetyDecision allowed() {
        return new SafetyDecision(true, false, List.of());
    }
}
