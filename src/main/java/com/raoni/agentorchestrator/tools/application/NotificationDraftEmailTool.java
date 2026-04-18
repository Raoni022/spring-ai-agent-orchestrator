package com.raoni.agentorchestrator.tools.application;

import com.raoni.agentorchestrator.tools.domain.AgentTool;
import com.raoni.agentorchestrator.tools.domain.ToolResult;
import org.springframework.stereotype.Component;

@Component
public class NotificationDraftEmailTool implements AgentTool {
    @Override
    public String name() {
        return "notification.draft_email";
    }

    @Override
    public String description() {
        return "Drafts a safe follow-up email without sending it.";
    }

    @Override
    public ToolResult execute(String input) {
        return new ToolResult(true, "Drafted follow-up email with qualification summary and proposed meeting slots");
    }
}
