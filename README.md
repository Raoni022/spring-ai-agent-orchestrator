# Spring AI Agent Orchestrator

A Java 21 and Spring Boot 3 project demonstrating API-first agentic workflow orchestration, tool calling, safety policies, auditability, and event-driven design concepts.

## Why this project exists

Many AI agent demos are UI-first: a chatbot clicks buttons, calls tools loosely, and hides operational decisions. This repository focuses on the backend side of agentic systems: explicit APIs, validated tool calls, policy checks, audit logs, deterministic local execution, and production-oriented boundaries.

## What this project demonstrates

- Java 21 and Spring Boot 3 backend design
- API-first agent orchestration
- Tool calling with typed contracts
- Agent planning and execution traces
- Human-in-the-loop approval for risky actions
- Prompt injection and unsafe instruction detection
- Audit logs for agent decisions
- Event-driven architecture concepts inspired by Azure Service Bus
- CI/CD with GitHub Actions
- Docker-based local development
- Testable architecture for agentic systems

## Architecture

```text
client
  -> Agent API
      -> safety policy check
      -> task planner
      -> tool registry
      -> tool execution engine
      -> audit log
      -> domain event publisher
      -> execution trace response
```

The current version uses deterministic local tools instead of real external APIs. This makes the project safe to run and easy to review while still showing the architecture required for production agent systems.

## API-first agents vs UI-based AI

UI-based AI is useful for human interaction. API-first agents are better for reliable automation because each action can be validated, audited, retried, authorized, and tested.

This project intentionally models agent tasks as backend requests instead of free-form UI interactions.

## Main endpoints

```http
POST /api/agents/tasks
GET /api/agents/tasks/{id}
GET /api/tools
```

## Quick start

```bash
mvn test
mvn spring-boot:run
```

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

## Example request

```bash
curl -X POST http://localhost:8080/api/agents/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "goal": "Qualify a CRM lead and prepare a calendar follow-up",
    "context": "Lead is interested in an automation project and asked for pricing.",
    "allowedTools": ["crm.lookup", "calendar.propose_slots", "notification.draft_email"],
    "requireHumanApproval": true
  }'
```

## Azure production mapping

| Local component | Azure production mapping |
|---|---|
| Spring Boot Agent API | AKS or Azure App Service |
| Agent events | Azure Service Bus |
| Long-running tasks | Azure Functions or Container Apps Jobs |
| Secrets | Azure Key Vault |
| Observability | Azure Monitor / Application Insights |
| CI/CD | GitHub Actions |

This repository does not claim to be deployed to Azure. It demonstrates a deployment-ready architecture direction.

## Safety model

The safety layer checks for prompt injection attempts, requests to bypass policies, attempts to reveal secrets or hidden prompts, unsafe tool usage, and risky actions that require human approval.

## Trade-offs

- Tools are mocked for deterministic local execution.
- The planner is rules-based to keep the project transparent.
- Real LLM integration can be added through Spring AI or LangChain4j.
- The project is a modular monolith, designed to evolve toward microservices.

## Author

Raoni Medeiros  
AI Automation & Systems Engineer
