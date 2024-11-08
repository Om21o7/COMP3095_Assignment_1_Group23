package ca.gbc.bookingservice.service;

import ca.gbc.bookingservice.dto.BookingRequest;
import ca.gbc.bookingservice.dto.BookingResponse;
import ca.gbc.bookingservice.model.Booking;
import ca.gbc.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    String updateServiceUrl = "http://room-service:9001/api/rooms/";
    // For inter-service communication with RoomService

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        // Check room availability
        String roomServiceUrl = "http://room-service:9001/api/rooms/availability/" + bookingRequest.roomId();
        Boolean isAvailable = restTemplate.getForObject(roomServiceUrl, Boolean.class);

        if (Boolean.FALSE.equals(isAvailable)) {
            throw new IllegalStateException("Room is not available for booking.");
        }

        // Create and save booking
        Booking booking = new Booking();
        booking.setRoomId(bookingRequest.roomId());
        booking.setUserName(bookingRequest.userName());
        booking.setStartTime(bookingRequest.startTime());
        booking.setEndTime(bookingRequest.endTime());

        Booking savedBooking = bookingRepository.save(booking);
        // Update room availability
        updateRoomAvailability(bookingRequest.roomId(), false);

        return new BookingResponse(
                savedBooking.getId(),
                savedBooking.getRoomId(),
                savedBooking.getUserName(),
                savedBooking.getStartTime(),
                savedBooking.getEndTime(),
                bookingRequest.purpose(),
                true
        );
    }

    private void updateRoomAvailability(String roomId, boolean available) {
        String url = String.format("%s/%s/availability?available=%b", updateServiceUrl, roomId, available);
        restTemplate.put(url, null);
    }



    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(booking -> new BookingResponse(
                        booking.getId(),
                        booking.getRoomId(),
                        booking.getUserName(),
                        booking.getStartTime(),
                        booking.getEndTime(),
                        null, // purpose can be added here if needed
                        true
                ))
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponse getBookingById(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        return new BookingResponse(
                booking.getId(),
                booking.getRoomId(),
                booking.getUserName(),
                booking.getStartTime(),
                booking.getEndTime(),
                null, // purpose can be added here if needed
                true
        );
    }

    @Override
    public void deleteBooking(String bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public Boolean bookingExists(String id) {
        log.debug("Checking if booking exists with ID: " + id);
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            return true;
        } else {
            return false;
        }
    }
}
