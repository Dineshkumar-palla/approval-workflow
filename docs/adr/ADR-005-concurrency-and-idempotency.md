# Use optimistic version checks

- **Status:** Accepted

## Context
Concurrent commands can act on stale aggregate snapshots; retries can duplicate effects.

## Decision
Repository save requires expected version and exactly one version increment; duplicate creates fail. No command idempotency key store.

## Alternatives considered
Last-write-wins or global locking only.

## Positive consequences
Stale writes are detected in adapter contract.

## Negative consequences
In-memory synchronization is process-local; retrying a successful command is a failure, not replayed result.

## Follow-up work
Use database conditional update and command receipt table in production.
