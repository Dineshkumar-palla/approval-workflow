package com.example.approval.domain;
import java.time.Instant; import java.util.UUID;
public sealed interface DomainEvent permits ExpenseSubmitted, ExpenseApproved, ExpenseRejected, ExpenseWithdrawn { UUID eventId(); ExpenseRequestId requestId(); Instant occurredAt(); }