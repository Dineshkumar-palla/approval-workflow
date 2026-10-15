# Use Hexagonal Architecture

- **Status:** Accepted

## Context
Application logic must be testable without frameworks or external systems.

## Decision
Keep domain pure, define ports in application, implement ports in adapters, wire them in bootstrap.

## Alternatives considered
Layered framework-centric application.

## Positive consequences
Fast deterministic tests and replaceable infrastructure.

## Negative consequences
More interfaces and explicit wiring.

## Follow-up work
Add production adapters without changing domain.
