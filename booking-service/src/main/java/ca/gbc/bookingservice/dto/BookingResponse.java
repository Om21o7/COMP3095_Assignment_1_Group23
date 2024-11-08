package ca.gbc.bookingservice.dto;

import java.time.LocalDateTime;

public record BookingResponse(
        String id,
        String roomId,
        String userName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String purpose,
        boolean confirmed
) {}
