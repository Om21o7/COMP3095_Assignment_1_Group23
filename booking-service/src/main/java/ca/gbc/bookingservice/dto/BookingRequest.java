package ca.gbc.bookingservice.dto;

import java.time.LocalDateTime;

public record BookingRequest(
        String roomId,
        String userName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String purpose
) {}
