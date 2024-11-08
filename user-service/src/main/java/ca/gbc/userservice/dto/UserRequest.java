package ca.gbc.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class UserRequest {

    private Long id;
    private String name;
    private String email;
    private String role;   // e.g., student, staff, faculty
    private String userType; // e.g., admin, regular, etc.
}
