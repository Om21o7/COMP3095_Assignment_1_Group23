package ca.gbc.eventservice.service;

import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;
import ca.gbc.eventservice.model.Event;
import ca.gbc.eventservice.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    @Autowired
    private final EventRepository eventRepository;

    private final RestTemplate restTemplate;

    String bookingServiceUrl = "http://booking-service:9004/api/bookings/exists/";
    String userServiceUrl = "http://user-service:9006/api/users/";


    @Override
    public String createEvent(EventRequest eventRequest) {

        String bookingId = eventRequest.BookingId();
        Boolean exists =restTemplate.getForObject(bookingServiceUrl + bookingId, Boolean.class);

        if (Boolean.FALSE.equals(exists)) {
            return "Booking not found with ID: " + bookingId;
        }
        else {
            String organizerEmail = eventRequest.organizer();  // Assuming this is the email
            if (!isOrganizerAuthorized(organizerEmail, eventRequest.location())) {
                return "Organizer is not authorized to create event in location: " + eventRequest.location();
            }
            else {
                Event event = mapRequestToEvent(eventRequest);
                Event savedEvent = eventRepository.save(event);
                return "Event created successfully with ID: " + event.getId();
            }

        }

    }


    private boolean isOrganizerAuthorized(String organizerId, String eventLocation) {

        String url = userServiceUrl + "role/" + organizerId;  // Use the endpoint we created earlier
        try {

            String role = restTemplate.getForObject(url, String.class);


            assert role != null;
            if (role.equals("faculty")) {
                return true;
            }


            return role.equals("student") && eventLocation.equalsIgnoreCase("George Brown College");


        } catch (Exception e) {
            log.error("Error checking organizer role for email: " + organizerId, e);
            return false;
        }
    }



    @Override
    public List<EventResponse> getAllEvents() {
        log.debug("Fetching all events.");
        List<Event> events = eventRepository.findAll();
        return events.stream().map(this::mapToEventResponse).toList();
    }

    @Override
    public EventResponse getEventById(String id) {
        log.debug("Fetching event by ID: " + id);
        Optional<Event> event = eventRepository.findById(id);
        return event.map(this::mapToEventResponse).orElse(null);
    }

    @Override
    public void deleteEvent(String id) {
        log.debug("Deleting event with ID: " + id);
        eventRepository.deleteById(id);
    }




    @Override
    public void updateEventdate(String id, String newDate) {
        log.debug("Updating event date for event ID: " + id);
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setDate(newDate);
            eventRepository.save(event);
        }
    }



    private EventResponse mapToEventResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getBookingId(),
                event.getName(),
                event.getDescription(),
                event.getOrganizer(),
                event.getDate(),
                event.getLocation()
        );
    }

    private Event mapRequestToEvent(EventRequest request) {
        return Event.builder()
                .BookingId(request.BookingId())
                .name(request.name())
                .description(request.description())
                .organizer(request.organizer())
                .date(request.date())
                .location(request.location())
                .build();
    }
}
