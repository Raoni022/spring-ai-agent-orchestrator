package com.raoni.agentorchestrator.tools.domain;

public interface AgentTool {
    String name();
    String description();
    ToolResult execute(String input);
}
