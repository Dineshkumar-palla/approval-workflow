package com.example.approval.application;
import com.example.approval.domain.*;
public final class Commands { private Commands(){} public record CreateExpenseRequest(ExpenseRequestId id,UserId owner,UserId approver,Money amount,String description){} public record SubmitExpenseRequest(ExpenseRequestId id){} public record ApproveExpense(ExpenseRequestId id,UserId actor){} public record RejectExpense(ExpenseRequestId id,UserId actor,String reason){} public record WithdrawExpense(ExpenseRequestId id,UserId actor){} }
