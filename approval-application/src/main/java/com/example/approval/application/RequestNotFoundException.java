package com.example.approval.application;
import com.example.approval.domain.ExpenseRequestId;
public class RequestNotFoundException extends ApplicationException { public RequestNotFoundException(ExpenseRequestId id){super("Expense request not found: "+id.value());} }