package com.example.approval.domain;
import java.time.Instant; import java.util.*;
/** Aggregate root. State transitions are atomic in memory: validate first, then mutate and append an event. */
public final class ExpenseRequest {
 private final ExpenseRequestId id; private final UserId owner; private final UserId approver; private final Money amount; private final String description; private final Instant createdAt;
 private RequestStatus status; private Instant updatedAt; private long version; private final List<DomainEvent> history;
 private ExpenseRequest(ExpenseRequestId id,UserId owner,UserId approver,Money amount,String description,Instant now,RequestStatus status,Instant updatedAt,long version,List<DomainEvent> history){
  if(id==null||owner==null||approver==null||amount==null||description==null||description.isBlank()||now==null||status==null||updatedAt==null||version<0) throw new InvalidRequestDataException("Invalid expense request data");
  if(owner.equals(approver)) throw new InvalidRequestDataException("Owner cannot be their own approver");
  this.id=id;this.owner=owner;this.approver=approver;this.amount=amount;this.description=description.trim();this.createdAt=now;this.status=status;this.updatedAt=updatedAt;this.version=version;this.history=new ArrayList<>(history);
 }
 public static ExpenseRequest draft(ExpenseRequestId id,UserId owner,UserId approver,Money amount,String description,Instant now){return new ExpenseRequest(id,owner,approver,amount,description,now,RequestStatus.DRAFT,now,0,List.of());}
 /** Repository adapters use a defensive snapshot so callers cannot mutate stored instances by alias. */
 public ExpenseRequest copy(){return new ExpenseRequest(id,owner,approver,amount,description,createdAt,status,updatedAt,version,history);}
 public void submit(Instant now){requireStatus(RequestStatus.DRAFT); requireTime(now); status=RequestStatus.PENDING; touch(now); history.add(new ExpenseSubmitted(id,UUID.randomUUID(),now));}
 public void approve(UserId actor,Instant now){requireStatus(RequestStatus.PENDING); requireActor(actor,approver); if(owner.equals(actor))throw new UnauthorizedActorException("Owner cannot approve own request"); requireTime(now); status=RequestStatus.APPROVED;touch(now);history.add(new ExpenseApproved(id,UUID.randomUUID(),now,actor));}
 public void reject(UserId actor,String reason,Instant now){requireStatus(RequestStatus.PENDING);requireActor(actor,approver);if(reason==null||reason.isBlank())throw new InvalidRequestDataException("Rejection reason is required");requireTime(now);status=RequestStatus.REJECTED;touch(now);history.add(new ExpenseRejected(id,UUID.randomUUID(),now,actor,reason.trim()));}
 public void withdraw(UserId actor,Instant now){requireStatus(RequestStatus.PENDING);requireActor(actor,owner);requireTime(now);status=RequestStatus.WITHDRAWN;touch(now);history.add(new ExpenseWithdrawn(id,UUID.randomUUID(),now,actor));}
 private void requireStatus(RequestStatus expected){if(status!=expected)throw new InvalidStateTransitionException("Cannot transition from "+status+"; expected "+expected);}
 private static void requireActor(UserId actual,UserId expected){if(actual==null||!actual.equals(expected))throw new UnauthorizedActorException("Actor is not authorized for this operation");}
 private static void requireTime(Instant now){if(now==null)throw new InvalidRequestDataException("Timestamp is required");}
 private void touch(Instant now){updatedAt=now;version++;}
 public ExpenseRequestId id(){return id;} public UserId owner(){return owner;} public UserId approver(){return approver;} public Money amount(){return amount;} public String description(){return description;} public Instant createdAt(){return createdAt;} public RequestStatus status(){return status;} public Instant updatedAt(){return updatedAt;} public long version(){return version;} public List<DomainEvent> history(){return List.copyOf(history);} public List<DomainEvent> eventsSince(int index){if(index<0||index>history.size())throw new IllegalArgumentException("Invalid history index");return List.copyOf(history.subList(index,history.size()));}
}
