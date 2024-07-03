package com.wikicoding.jwt_auth.controller;

import com.wikicoding.jwt_auth.dtos.AuthRequest;
import com.wikicoding.jwt_auth.dtos.AuthResponse;
import com.wikicoding.jwt_auth.dtos.LoginRequest;
import com.wikicoding.jwt_auth.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(@RequestBody AuthRequest request){
//        return ResponseEntity.ok(authService.logout(request));
//    }
}
