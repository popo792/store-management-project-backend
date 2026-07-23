package com.store.database.controller;

import com.store.database.dto.LoginRequest;
import com.store.database.dto.AuthResponse;
import com.store.database.model.User;
import com.store.database.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request) {
        
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());

        if (userOptional.isPresent()) {
            User databaseUser = userOptional.get();
            
            if (databaseUser.getPassword().equals(request.getPassword())) {
                return ResponseEntity.ok(new AuthResponse("Login successful!", "fake-jwt-token-123", databaseUser.getRole()));
            }
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}