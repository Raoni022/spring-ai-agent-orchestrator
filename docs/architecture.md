# Architecture

This project demonstrates API-first agent orchestration using a modular Spring Boot backend.

## Modules

```text
agents      -> task API, orchestration service, in-memory task store
planning    -> deterministic planner that selects allowed tools
tools       -> typed tool contracts, registry, mock implementations
safety      -> prompt injection and policy checks
audit       -> audit boundary for decisions and actions
messaging   -> domain event boundary for future async integration
```

## Flow

```text
POST /api/agents/tasks
  -> validate request
  -> evaluate safety policy
  -> create tool execution plan
  -> execute allowed tools
  -> build execution trace
  -> require human approval when requested
  -> record audit event
  -> publish domain event
```

## Production evolution

The local implementation is intentionally deterministic. In production, the same boundaries can evolve into:

- LLM-based planner through Spring AI or LangChain4j
- Azure Service Bus for task and tool events
- persistent task state in PostgreSQL or Cosmos DB
- distributed workers for long-running tool execution
- Azure Key Vault for tool credentials
- Application Insights for traces and metrics
