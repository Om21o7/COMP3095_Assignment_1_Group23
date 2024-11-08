package ca.gbc.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;
    private String roomId;
    private String userName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
