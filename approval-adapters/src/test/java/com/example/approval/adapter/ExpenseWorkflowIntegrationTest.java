package com.example.approval.adapter;

import com.example.approval.application.*;
import com.example.approval.domain.*;import org.junit.jupiter.api.*;import java.time.Instant;import static org.assertj.core.api.Assertions.*;
class ExpenseWorkflowIntegrationTest { InMemoryExpenseRequestRepository repo;RecordingEventPublisher events;RecordingNotificationAdapter notices;ExpenseWorkflow flow;UserId owner=new UserId("o"),manager=new UserId("m");ExpenseRequestId id=ExpenseRequestId.newId();
@BeforeEach void setup(){repo=new InMemoryExpenseRequestRepository();events=new RecordingEventPublisher();notices=new RecordingNotificationAdapter();flow=new ExpenseWorkflow(repo,new FixedClockAdapter(Instant.parse("2026-02-01T00:00:00Z")),events,notices);flow.create(new Commands.CreateExpenseRequest(id,owner,manager,Money.of("5.00","USD"),"meal"));}
@Test void persistsAndPublishesSuccessfulTransition(){flow.submit(new Commands.SubmitExpenseRequest(id));assertThat(repo.findById(id).orElseThrow().status()).isEqualTo(RequestStatus.PENDING);assertThat(events.published()).hasSize(1);assertThat(notices.sent()).hasSize(1);}
@Test void missingRequestFails(){assertThatThrownBy(()->flow.submit(new Commands.SubmitExpenseRequest(ExpenseRequestId.newId()))).isInstanceOf(RequestNotFoundException.class);}
@Test void unauthorizedTransitionDoesNotNotify(){flow.submit(new Commands.SubmitExpenseRequest(id));assertThatThrownBy(()->flow.approve(new Commands.ApproveExpense(id,owner))).isInstanceOf(UnauthorizedActorException.class);assertThat(notices.sent()).hasSize(1);assertThat(events.published()).hasSize(1);}
@Test void duplicateCommandFailsDeterministically(){flow.submit(new Commands.SubmitExpenseRequest(id));assertThatThrownBy(()->flow.submit(new Commands.SubmitExpenseRequest(id))).isInstanceOf(InvalidStateTransitionException.class);assertThat(notices.sent()).hasSize(1);}
@Test void createDuplicateIdFails(){assertThatThrownBy(()->flow.create(new Commands.CreateExpenseRequest(id,owner,manager,Money.of("2","USD"),"again"))).isInstanceOf(VersionConflictException.class);}
}
