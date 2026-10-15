package com.example.approval.domain;
import java.util.Objects;
public record UserId(String value) { public UserId { if (value == null || value.isBlank()) throw new InvalidRequestDataException("User ID must not be blank"); value=value.trim(); } }