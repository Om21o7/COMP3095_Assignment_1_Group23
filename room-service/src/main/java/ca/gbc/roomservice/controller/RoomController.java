package ca.gbc.roomservice.controller;

import ca.gbc.roomservice.dto.RoomRequest;
import ca.gbc.roomservice.dto.RoomResponse;
import ca.gbc.roomservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<RoomResponse> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long roomId) {
        RoomResponse room = roomService.getRoomById(roomId);
        return room != null ? ResponseEntity.ok(room) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<RoomResponse> addRoom(@RequestBody RoomRequest request) {
        RoomResponse newRoom = roomService.addRoom(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<Long> updateRoom(@PathVariable Long roomId, @RequestBody RoomRequest roomDetails) {
        Long updatedRoomId = roomService.updateRoom(roomId, roomDetails);
        return updatedRoomId != null ? ResponseEntity.ok(updatedRoomId) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms() {
        List<RoomResponse> availableRooms = roomService.getAvailableRooms();
        return ResponseEntity.ok(availableRooms);
    }

    @GetMapping("/availability/{roomId}")
    public ResponseEntity<Boolean> checkRoomAvailability(@PathVariable Long roomId) {
        Boolean availability = roomService.checkRoomAvailability(roomId);
        return availability != null ? ResponseEntity.ok(availability) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{roomId}/availability")
    public ResponseEntity<Void> updateRoomAvailability(@PathVariable Long roomId, @RequestParam boolean available) {
        roomService.updateRoomAvailability(roomId, available);
        return ResponseEntity.noContent().build();
    }
}
