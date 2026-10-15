# Keep transitions in the aggregate

- **Status:** Accepted

## Context
Workflow transitions have authorization and state invariants.

## Decision
ExpenseRequest owns transition checks and mutation; illegal transitions throw domain exceptions.

## Alternatives considered
Rules exclusively in handlers or generic state machine library.

## Positive consequences
Rules remain consistent across entry points.

## Negative consequences
Aggregate is mutable within a command and must be copied at repository boundaries.

## Follow-up work
Consider richer policy objects only if requirements grow.
