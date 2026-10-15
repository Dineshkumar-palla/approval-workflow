package com.example.approval.domain;
import java.util.UUID;
public record ExpenseRequestId(UUID value) { public ExpenseRequestId { if(value==null) throw new InvalidRequestDataException("Request ID is required"); } public static ExpenseRequestId newId(){return new ExpenseRequestId(UUID.randomUUID());} }