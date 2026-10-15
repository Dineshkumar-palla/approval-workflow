# Separate domain events from notifications

- **Status:** Accepted

## Context
External effects must not be part of domain invariants.

## Decision
Application persists first, then publishes events and notifies; failures are surfaced but do not undo persistence. No outbox is implemented.

## Alternatives considered
Pretend in-memory effects are transactional or couple notifications to entity.

## Positive consequences
Simple runnable demonstration with clear boundary.

## Negative consequences
State and side effects can diverge on process/provider failure.

## Follow-up work
Implement transactional outbox and retry/idempotency policy for production.
