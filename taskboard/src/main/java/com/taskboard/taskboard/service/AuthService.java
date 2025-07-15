package com.taskboard.taskboard.service;

import com.taskboard.taskboard.dto.AuthResponse;
import com.taskboard.taskboard.dto.SigninRequest;
import com.taskboard.taskboard.dto.SignupRequest;
import com.taskboard.taskboard.entity.RefreshToken;
import com.taskboard.taskboard.entity.User;
import com.taskboard.taskboard.repository.RefreshTokenRepository;
import com.taskboard.taskboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${app.jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    public void signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use.");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .displayName(request.getDisplayName())
                .build();

        userRepository.save(user);
    }

    public AuthResponse signin(SigninRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid username or password");
        }

        String accessToken = jwtService.generateToken(user.getUsername());
//        String refreshToken = createRefreshToken(user);

        return AuthResponse.builder()
                .token(accessToken)
//                .refreshToken(refreshToken)
                .username(user.getUsername())
                .build();
    }

    private String createRefreshToken(User user) {
        String token = UUID.randomUUID().toString();

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(token)
                .expiryDate(LocalDateTime.now().plus(Duration.ofMillis(refreshTokenDurationMs)))
                .build();

        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public AuthResponse refreshAccessToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please sign in again.");
        }

        User user = token.getUser();
        String newAccessToken = jwtService.generateToken(user.getUsername());

        return AuthResponse.builder()
                .token(newAccessToken)
                .refreshToken(refreshToken) // Return the same refresh token
                .username(user.getUsername())
                .build();
    }

    public void logout(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}
