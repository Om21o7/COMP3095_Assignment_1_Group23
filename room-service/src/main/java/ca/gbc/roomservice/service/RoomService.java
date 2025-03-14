package ca.gbc.roomservice.service;

import ca.gbc.roomservice.dto.RoomRequest;
import ca.gbc.roomservice.dto.RoomResponse;

import java.util.List;

public interface RoomService {

    List<RoomResponse> getAllRooms();

    RoomResponse getRoomById(Long roomId);

    RoomResponse addRoom(RoomRequest request);

    Long updateRoom(Long roomId, RoomRequest roomDetails);

    void deleteRoom(Long roomId);

    List<RoomResponse> getAvailableRooms();

    Boolean checkRoomAvailability(Long roomId);

    void updateRoomAvailability(Long roomId, boolean available);
}
