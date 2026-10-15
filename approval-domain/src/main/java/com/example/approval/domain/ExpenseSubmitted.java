package com.example.approval.domain;
import java.time.Instant; import java.util.UUID;
public record ExpenseSubmitted(ExpenseRequestId requestId, java.util.UUID eventId, java.time.Instant occurredAt) implements DomainEvent { public ExpenseSubmitted { if(requestId==null||eventId==null||occurredAt==null) throw new InvalidRequestDataException("Event identity and timestamp are required"); } }