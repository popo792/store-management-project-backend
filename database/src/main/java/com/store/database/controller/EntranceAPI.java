package com.store.database.controller;

import com.store.database.security.CipherForge;
import com.store.database.repository.UserRepository;
import com.store.database.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

@RestController
@RequestMapping("/api/gate")
@CrossOrigin(origins = "http://localhost:4200")
public class EntranceAPI {

    private final UserRepository identityArchive;
    private final CipherForge tokenSmith;

    public EntranceAPI(UserRepository identityArchive, CipherForge tokenSmith) {
        this.identityArchive = identityArchive;
        this.tokenSmith = tokenSmith;
    }

    @PostMapping("/knock")
    public ResponseEntity<?> processEntry(@RequestBody Map<String, String> credentials) {
        String providedAlias = credentials.get("username");
        String providedShadow = credentials.get("password");

        List<User> roster = identityArchive.findAll();
        User foundEntity = roster.stream()
                .filter(u -> u.getUsername().equals(providedAlias))
                .filter(u -> u.getPassword().equals(providedShadow))
                .findFirst()
                .orElse(null);

        if (foundEntity != null) {
            String mintedSeal = tokenSmith.mintWristband(foundEntity.getUsername(), foundEntity.getRole());

            Map<String, String> payload = new HashMap<>();
            payload.put("token", mintedSeal);
            payload.put("role", foundEntity.getRole());

            return ResponseEntity.ok(payload);
        }

        return ResponseEntity.status(401).body("Access Denied");
    }
}