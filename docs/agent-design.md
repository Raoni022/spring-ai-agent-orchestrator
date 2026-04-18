# Agent Design

The project intentionally separates agent behavior into four layers:

1. Safety policy
2. Planning
3. Tool execution
4. Trace and audit

## Planner

The current planner is deterministic. It selects only tools explicitly allowed by the request and supported by the registry. This avoids hidden autonomous behavior and keeps the system testable.

## Tool calling

Each tool implements a typed backend contract:

```java
public interface AgentTool {
    String name();
    String description();
    ToolResult execute(String input);
}
```

This keeps tool execution explicit, auditable, and replaceable.

## Human approval

When `requireHumanApproval` is true, the workflow produces a trace and returns `WAITING_FOR_APPROVAL`. This models a safer design for agentic systems where external side effects should not happen silently.

## Future LLM integration

A real LLM planner can be added later through Spring AI or LangChain4j, but the backend contracts should stay the same.
