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


    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<EventResponse> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }


    @GetMapping("/{id}")
    public ResponseEntity<String> getEventById(@PathVariable String id) {
        EventResponse eventResponse = eventService.getEventById(id);
        return eventResponse != null ? ResponseEntity.ok(eventResponse.toString()) : ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventRequest eventRequest) {


        String eventResponse = eventService.createEvent(eventRequest);


        if (eventResponse == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).header("error", "Organizer is not authorized").build();
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(eventResponse);
    }



    @PutMapping("/{id}/date")
    public ResponseEntity<Void> updateEventDate(@PathVariable String id, @RequestParam String newDate) {
        eventService.updateEventdate(id, newDate);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
