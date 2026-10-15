package com.example.approval.adapter;
import com.example.approval.application.ClockPort; import java.time.Instant; import java.util.Objects;
public final class FixedClockAdapter implements ClockPort { private final Instant instant; public FixedClockAdapter(Instant instant){this.instant=Objects.requireNonNull(instant);} public Instant now(){return instant;} }