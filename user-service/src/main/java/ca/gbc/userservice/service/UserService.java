package ca.gbc.userservice.service;

import ca.gbc.userservice.dto.UserRequest;
import ca.gbc.userservice.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse createUser(UserRequest request);
    List<UserResponse> getAllUsers();
    Optional<UserResponse> getUserById(Long id);
    Optional<UserResponse> updateUser(Long id, UserRequest userRequest);
    boolean deleteUser(Long id);
    String getUserRoleByEmail(String email);
}
