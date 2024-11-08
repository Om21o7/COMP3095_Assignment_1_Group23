package ca.gbc.eventservice.service;

import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;
import ca.gbc.eventservice.model.Event;

import java.util.List;

public interface EventService {
   String createEvent(EventRequest eventRequest);
    List<EventResponse> getAllEvents();
    EventResponse getEventById(String id);
    void deleteEvent(String id);
    void updateEventdate(String id,String newDate);

}
