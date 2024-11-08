package ca.gbc.userservice.service;

import ca.gbc.userservice.dto.UserRequest;
import ca.gbc.userservice.dto.UserResponse;
import ca.gbc.userservice.model.User;
import ca.gbc.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        // Check if user already exists (e.g., by email)
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        // Create a new User entity from the UserRequest DTO
        User user = User.builder()
                .username(userRequest.getName())
                .email(userRequest.getEmail())
                .role(userRequest.getRole())
                .userType(userRequest.getUserType())
                .build();

        // Save the user to the repository
        userRepository.save(user);

        // Return mapped UserResponse
        return mapToUserResponse(user);
    }


    @Override
    public List<UserResponse> getAllUsers() {
        // Fetch all users from the database
        List<User> users = userRepository.findAll();

        // Map all User entities to UserResponse DTOs and return
        return users.stream().map(this::mapToUserResponse).toList();
    }

    @Override
    public Optional<UserResponse> getUserById(Long id) {
        // Fetch user by ID from the repository
        Optional<User> user = userRepository.findById(id);

        // If user is found, map to UserResponse DTO, otherwise return empty
        return user.map(this::mapToUserResponse);
    }

    @Override
    public Optional<UserResponse> updateUser(Long id, UserRequest userRequest) {
        // Fetch the user by ID from the repository
        Optional<User> userOptional = userRepository.findById(id);

        // If user exists, update the fields with the new data
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setRole(userRequest.getRole());
            user.setUserType(userRequest.getUserType());
            userRepository.save(user);

            // Return the updated UserResponse DTO
            return Optional.of(mapToUserResponse(user));
        }

        // Return empty if the user with the given ID doesn't exist
        return Optional.empty();
    }

    @Override
    public boolean deleteUser(Long id) {
        // Try to find the user by ID
        Optional<User> user = userRepository.findById(id);

        // If user exists, delete it and return true
        user.ifPresent(userRepository::delete);

        // Return true if user was found and deleted, false otherwise
        return user.isPresent();
    }

    @Override
    public String getUserRoleByEmail(String email) {
        // Fetch the user by email
        Optional<User> user = userRepository.findByEmail(email);

        // If user exists, return the role, otherwise return null or a default value
        return user.map(User::getRole).orElse(null);  // You can modify this if needed
    }

    // Helper method to map User entity to UserResponse DTO
    private UserResponse mapToUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getUserType()
        );
    }
}
