# AI Safety and Security

This repository models safety as a backend boundary rather than a prompt-only concern.

## Included controls

- Prompt injection detection
- Policy bypass detection
- Secret exfiltration detection
- Tool allow-listing per request
- Human approval mode for risky workflows
- Execution trace generation
- Audit event boundary

## Why this matters

Agentic systems are different from chatbots because they can take actions. Tool calls need authorization, validation, logging, and rollback-aware design.

## Limitations

The current policy is rule-based and intentionally transparent. A production system should add:

- structured tool permissions
- authentication and authorization
- tenant isolation
- model-assisted risk scoring
- approval workflows
- rate limits
- secret management through Azure Key Vault
