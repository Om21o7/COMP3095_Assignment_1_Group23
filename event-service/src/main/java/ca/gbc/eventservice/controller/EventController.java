package ca.gbc.eventservice.controller;

import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;
import ca.gbc.eventservice.model.Event;
import ca.gbc.eventservice.service.EventService;
import ca.gbc.eventservice.service.EventServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventServiceImpl eventService;

    // Get all events
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<EventResponse> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    // Get event by ID
    @GetMapping("/{id}")
    public ResponseEntity<String> getEventById(@PathVariable String id) {
        EventResponse eventResponse = eventService.getEventById(id);
        return eventResponse != null ? ResponseEntity.ok(eventResponse.toString()) : ResponseEntity.notFound().build();
    }

    // Create a new event
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventRequest eventRequest) {
        // Create the event using the service

        String eventResponse = eventService.createEvent(eventRequest);

        // If the event is null, it means there was an error (either bookingId doesn't exist or organizer is not authorized)
        if (eventResponse == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).header("error", "Organizer is not authorized").build();
        }

        // Return the created event response
        return ResponseEntity.status(HttpStatus.CREATED).body(eventResponse);
    }


    // Update event date
    @PutMapping("/{id}/date")
    public ResponseEntity<Void> updateEventDate(@PathVariable String id, @RequestParam String newDate) {
        eventService.updateEventdate(id, newDate);
        return ResponseEntity.noContent().build();
    }

    // Delete event by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
