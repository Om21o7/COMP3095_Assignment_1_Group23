package ca.gbc.gbc_event_booking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class GbcEventBookingApplicationTests {

    @Test
    void contextLoads() {
    }

}
