package com.example.approval.application;
import com.example.approval.domain.DomainEvent;
public interface DomainEventPublisher { void publish(DomainEvent event); }