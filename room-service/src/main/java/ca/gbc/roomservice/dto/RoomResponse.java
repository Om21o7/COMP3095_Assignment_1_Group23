package ca.gbc.roomservice.dto;

public record RoomResponse(
        Long id,
        String roomName,
        int capacity,
        String features, // e.g., projector, whiteboard
        boolean availability
) {}
