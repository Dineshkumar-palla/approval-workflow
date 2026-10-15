# Inject time and retain event history

- **Status:** Accepted

## Context
Transition timestamps and auditability are business-relevant.

## Decision
Application ClockPort supplies Instant; aggregate stores immutable event records in append-only history.

## Alternatives considered
Call system clock in domain; publish only ephemeral events.

## Positive consequences
Deterministic tests and inspectable history.

## Negative consequences
History grows with aggregate; event dispatch needs separate reliability treatment.

## Follow-up work
Define retention/projection strategy for large volumes.
