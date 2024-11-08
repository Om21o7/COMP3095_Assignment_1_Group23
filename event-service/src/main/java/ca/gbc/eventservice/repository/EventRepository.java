package ca.gbc.eventservice.repository;

import ca.gbc.eventservice.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
    // You can define custom query methods here if needed
}
