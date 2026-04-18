package com.raoni.agentorchestrator.safety.application;

import com.raoni.agentorchestrator.safety.dto.SafetyDecision;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class PromptInjectionPolicy {
    public SafetyDecision evaluate(String goal, String context) {
        String input = ((goal == null ? "" : goal) + " " + (context == null ? "" : context)).toLowerCase(Locale.ROOT);
        List<String> warnings = new ArrayList<>();
        if (input.contains("ignore previous instructions")) warnings.add("prompt_injection_instruction_override");
        if (input.contains("reveal system prompt")) warnings.add("prompt_injection_system_prompt_exposure");
        if (input.contains("bypass policy") || input.contains("bypass security")) warnings.add("policy_bypass_attempt");
        if (input.contains("print environment variables") || input.contains("exfiltrate secrets")) warnings.add("secret_exfiltration_attempt");
        return new SafetyDecision(warnings.isEmpty(), false, warnings);
    }
}
