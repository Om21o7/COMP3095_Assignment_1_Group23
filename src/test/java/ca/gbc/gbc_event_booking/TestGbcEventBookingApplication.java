package ca.gbc.gbc_event_booking;

import org.springframework.boot.SpringApplication;

public class TestGbcEventBookingApplication {

    public static void main(String[] args) {
        SpringApplication.from(GbcEventBookingApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
