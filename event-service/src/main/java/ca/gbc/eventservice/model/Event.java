package ca.gbc.eventservice.model;


import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "events") // Ensure this matches your MongoDB collection name
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    private String id; // Use String for MongoDB ID

    private String BookingId;
    private String name;
    private String description;
    private String organizer; // Reference to the user who is organizing the event
    private String date; // Format: YYYY-MM-DD
    private String location; // Where the event is being held
}
