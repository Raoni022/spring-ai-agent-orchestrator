package com.raoni.agentorchestrator.tools.application;

import com.raoni.agentorchestrator.tools.domain.AgentTool;
import com.raoni.agentorchestrator.tools.domain.ToolResult;
import org.springframework.stereotype.Component;

@Component
public class CalendarProposeSlotsTool implements AgentTool {
    @Override
    public String name() {
        return "calendar.propose_slots";
    }

    @Override
    public String description() {
        return "Proposes deterministic follow-up time slots for a lead.";
    }

    @Override
    public ToolResult execute(String input) {
        return new ToolResult(true, "Proposed slots: Tuesday 10:00, Wednesday 14:00, Friday 09:30");
    }
}
