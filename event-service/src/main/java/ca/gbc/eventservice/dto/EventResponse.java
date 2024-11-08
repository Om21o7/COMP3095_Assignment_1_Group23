package ca.gbc.eventservice.dto;


import lombok.extern.slf4j.Slf4j;

@Slf4j

public record EventResponse(
        String id,
        String BookingId,
        String name,
        String description,
        String organizer,
        String date,
        String location
) {}
