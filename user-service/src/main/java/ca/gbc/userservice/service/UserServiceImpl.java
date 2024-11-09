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

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }


        User user = User.builder()
                .username(userRequest.getName())
                .email(userRequest.getEmail())
                .role(userRequest.getRole())
                .userType(userRequest.getUserType())
                .build();


        userRepository.save(user);


        return mapToUserResponse(user);
    }


    @Override
    public List<UserResponse> getAllUsers() {

        List<User> users = userRepository.findAll();


        return users.stream().map(this::mapToUserResponse).toList();
    }

    @Override
    public Optional<UserResponse> getUserById(Long id) {

        Optional<User> user = userRepository.findById(id);


        return user.map(this::mapToUserResponse);
    }

    @Override
    public Optional<UserResponse> updateUser(Long id, UserRequest userRequest) {

        Optional<User> userOptional = userRepository.findById(id);


        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setRole(userRequest.getRole());
            user.setUserType(userRequest.getUserType());
            userRepository.save(user);


            return Optional.of(mapToUserResponse(user));
        }


        return Optional.empty();
    }

    @Override
    public boolean deleteUser(Long id) {

        Optional<User> user = userRepository.findById(id);


        user.ifPresent(userRepository::delete);


        return user.isPresent();
    }

    @Override
    public String getUserRoleByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);


        return user.map(User::getRole).orElse(null);
    }


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
