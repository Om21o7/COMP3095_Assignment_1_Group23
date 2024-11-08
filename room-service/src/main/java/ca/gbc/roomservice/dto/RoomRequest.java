package ca.gbc.roomservice.dto;

public record RoomRequest(
        String roomName,
        int capacity,
        String features, // e.g., projector, whiteboard
        boolean availability
) {}
