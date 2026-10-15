package com.example.approval.application;
import com.example.approval.domain.*; import java.util.*;
/** Coordinates ports. Persistence occurs before external effects; event/notification failures are visible but not atomic. */
public final class ExpenseWorkflow {
 private final ExpenseRequestRepository repository; private final ClockPort clock; private final DomainEventPublisher publisher; private final NotificationPort notifications;
 public ExpenseWorkflow(ExpenseRequestRepository repository,ClockPort clock,DomainEventPublisher publisher,NotificationPort notifications){this.repository=Objects.requireNonNull(repository);this.clock=Objects.requireNonNull(clock);this.publisher=Objects.requireNonNull(publisher);this.notifications=Objects.requireNonNull(notifications);}
 public ExpenseRequest create(Commands.CreateExpenseRequest c){Objects.requireNonNull(c); ExpenseRequest request=ExpenseRequest.draft(c.id(),c.owner(),c.approver(),c.amount(),c.description(),clock.now());repository.saveNew(request);return request.copy();}
 public ExpenseRequest submit(Commands.SubmitExpenseRequest c){return transition(c.id(),r->r.submit(clock.now()));}
 public ExpenseRequest approve(Commands.ApproveExpense c){return transition(c.id(),r->r.approve(c.actor(),clock.now()));}
 public ExpenseRequest reject(Commands.RejectExpense c){return transition(c.id(),r->r.reject(c.actor(),c.reason(),clock.now()));}
 public ExpenseRequest withdraw(Commands.WithdrawExpense c){return transition(c.id(),r->r.withdraw(c.actor(),clock.now()));}
 private ExpenseRequest transition(ExpenseRequestId id, java.util.function.Consumer<ExpenseRequest> action){ExpenseRequest stored=repository.findById(Objects.requireNonNull(id)).orElseThrow(()->new RequestNotFoundException(id)); ExpenseRequest changed=stored.copy(); long expected=stored.version(); int before=changed.history().size(); action.accept(changed); List<DomainEvent> emitted=changed.eventsSince(before); repository.save(changed,expected); for(DomainEvent event:emitted){publisher.publish(event);notifications.notify(event);} return changed.copy();}
}
