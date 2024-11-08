package ca.gbc.eventservice.dto;



public record EventRequest(
        String id,
        String BookingId,
        String name,
        String description,
        String organizer,
        String date,
        String location
) {}
