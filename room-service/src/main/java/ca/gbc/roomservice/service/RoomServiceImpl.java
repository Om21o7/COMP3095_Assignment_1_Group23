package ca.gbc.roomservice.service;

import ca.gbc.roomservice.dto.RoomRequest;
import ca.gbc.roomservice.dto.RoomResponse;
import ca.gbc.roomservice.model.Room;
import ca.gbc.roomservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    @Autowired
    private final RoomRepository roomRepository;

    @Override
    public List<RoomResponse> getAllRooms() {
        log.debug("Returning a list of all rooms.");
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(this::mapToRoomResponse).toList();
    }

    private RoomResponse mapToRoomResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getRoomName(),
                room.getCapacity(),
                room.getFeatures(),
                room.isAvailability()
        );
    }

    private Room mapRequestToRoom(RoomRequest request) {
        return Room.builder()
                .roomName(request.roomName())
                .capacity(request.capacity())
                .features(request.features())
                .availability(request.availability())
                .build();
    }

    @Override
    public RoomResponse getRoomById(Long roomId) {
        log.debug("Getting room by ID: " + roomId);
        Room room = roomRepository.findById(String.valueOf(roomId)).orElse(null);
        return (room != null) ? mapToRoomResponse(room) : null;
    }

    @Override
    public RoomResponse addRoom(RoomRequest request) {
        log.debug("Adding a new room with name: " + request.roomName());
        Room room = mapRequestToRoom(request);
        roomRepository.save(room);
        return mapToRoomResponse(room);
    }

    @Override
    public Long updateRoom(Long roomId, RoomRequest roomDetails) {
        log.debug("Updating room with ID: " + roomId);
        Room room = roomRepository.findById(String.valueOf(roomId)).orElse(null);
        if (room != null) {
            room.setRoomName(roomDetails.roomName());
            room.setCapacity(roomDetails.capacity());
            room.setFeatures(roomDetails.features());
            room.setAvailability(roomDetails.availability());
            return roomRepository.save(room).getId();
        }
        return null;
    }

    @Override
    public void deleteRoom(Long roomId) {
        log.debug("Deleting room with ID: " + roomId);
        roomRepository.deleteById(String.valueOf(roomId));
    }

    @Override
    public List<RoomResponse> getAvailableRooms() {
        log.debug("Returning a list of available rooms.");
        List<Room> rooms = roomRepository.findByAvailability(true);
        return rooms.stream().map(this::mapToRoomResponse).toList();
    }

    @Override
    public Boolean checkRoomAvailability(Long roomId) {
        log.debug("Checking availability for room ID: " + roomId);
        Room room = roomRepository.findById(String.valueOf(roomId)).orElse(null);
        return (room != null) ? room.isAvailability() : null;
    }

    @Override
    public void updateRoomAvailability(Long roomId, boolean available) {
        log.debug("Updating availability for room ID: " + roomId);
        Room room = roomRepository.findById(String.valueOf(roomId)).orElse(null);
        if (room != null) {
            room.setAvailability(available);
            roomRepository.save(room);
        }
    }
}
