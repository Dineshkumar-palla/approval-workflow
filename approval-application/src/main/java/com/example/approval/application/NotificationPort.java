package com.example.approval.application;
import com.example.approval.domain.DomainEvent;
public interface NotificationPort { void notify(DomainEvent event); }