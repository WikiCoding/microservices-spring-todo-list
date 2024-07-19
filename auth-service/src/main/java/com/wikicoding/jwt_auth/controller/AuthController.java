package com.wikicoding.jwt_auth.controller;

import com.wikicoding.jwt_auth.dtos.AuthRequest;
import com.wikicoding.jwt_auth.dtos.AuthResponse;
import com.wikicoding.jwt_auth.dtos.LoginRequest;
import com.wikicoding.jwt_auth.service.AuthenticationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authService;

    @PostMapping("/register")
    @CircuitBreaker(name = "register", fallbackMethod = "fallbackMethod")
    @Retry(name = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    public ResponseEntity<AuthResponse> fallbackMethod(AuthRequest authRequest, RuntimeException runtimeException) {
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
    }
}
