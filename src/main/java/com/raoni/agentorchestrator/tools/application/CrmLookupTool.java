package com.raoni.agentorchestrator.tools.application;

import com.raoni.agentorchestrator.tools.domain.AgentTool;
import com.raoni.agentorchestrator.tools.domain.ToolResult;
import org.springframework.stereotype.Component;

@Component
public class CrmLookupTool implements AgentTool {
    @Override
    public String name() {
        return "crm.lookup";
    }

    @Override
    public String description() {
        return "Looks up CRM context for a lead using deterministic local data.";
    }

    @Override
    public ToolResult execute(String input) {
        return new ToolResult(true, "CRM lead found: segment=SMB, intent=automation, priority=medium");
    }
}
