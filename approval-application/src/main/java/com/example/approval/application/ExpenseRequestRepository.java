package com.example.approval.application;
import com.example.approval.domain.*; import java.util.Optional;
public interface ExpenseRequestRepository { Optional<ExpenseRequest> findById(ExpenseRequestId id); void saveNew(ExpenseRequest request); void save(ExpenseRequest request,long expectedVersion); }