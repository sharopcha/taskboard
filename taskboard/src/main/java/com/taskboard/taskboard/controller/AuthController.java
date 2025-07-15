package com.taskboard.taskboard.controller;

import com.taskboard.taskboard.dto.AuthResponse;
import com.taskboard.taskboard.dto.SigninRequest;
import com.taskboard.taskboard.dto.SignupRequest;
import com.taskboard.taskboard.entity.User;
import com.taskboard.taskboard.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@Valid @RequestBody SigninRequest request) {
        AuthResponse response = authService.signin(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        AuthResponse response = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal User user) {
        authService.logout(user);
        return ResponseEntity.ok("Logged out successfully");
    }
}
