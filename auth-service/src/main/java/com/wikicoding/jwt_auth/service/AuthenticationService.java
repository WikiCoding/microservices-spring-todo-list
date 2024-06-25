package com.wikicoding.jwt_auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wikicoding.jwt_auth.entity.Token;
import com.wikicoding.jwt_auth.entity.TokenType;
import com.wikicoding.jwt_auth.entity.User;
import com.wikicoding.jwt_auth.repository.TokenRepository;
import com.wikicoding.jwt_auth.repository.UserRepository;
import com.wikicoding.jwt_auth.security_config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Service to handle business logic
 **/
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(RegisterReqDTO request) {
        User user = new User(request.getUsername(),
                passwordEncoder.encode(request.getPassword()), request.getRole());

        User savedUser = userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);

        return new AuthResponseDTO(jwtToken, refreshToken);
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return new AuthResponseDTO(jwtToken, refreshToken);
    }

    public String logout(AuthRequestDTO request) {
        User loggedUser = new User();
        loggedUser.setUsername(request.getUsername());
        loggedUser.setPassword(request.getPassword());
        revokeAllUserTokens(loggedUser);
        return "logged out";
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token(jwtToken, TokenType.BEARER, false, false, user);

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUsername(refreshToken);
        if (userName != null) {
            User user = this.userRepository.findByUsername(userName)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthResponseDTO authResponse = new AuthResponseDTO(accessToken, refreshToken);

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
