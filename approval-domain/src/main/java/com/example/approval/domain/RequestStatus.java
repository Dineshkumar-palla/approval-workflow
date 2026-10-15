package com.example.approval.domain;
public enum RequestStatus { DRAFT, PENDING, APPROVED, REJECTED, WITHDRAWN; public boolean terminal(){return this==APPROVED||this==REJECTED||this==WITHDRAWN;} }