package com.wikicoding.jwt_auth.service;

import com.wikicoding.jwt_auth.dtos.AuthRequest;
import com.wikicoding.jwt_auth.dtos.AuthResponse;
import com.wikicoding.jwt_auth.dtos.LoginRequest;
import com.wikicoding.jwt_auth.dtos.UserVO;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service to handle business logic
 **/
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public AuthResponse register(AuthRequest request) {
        //do validation if user exists in DB
        UserVO reqUser = restTemplate.getForObject("http://users-service/users/{email}",
                UserVO.class,
                request.getEmail());

        if (reqUser != null) throw new IllegalArgumentException("User already registered");

        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));

        UserVO registeredUser = restTemplate.postForObject("http://users-service/users", request, UserVO.class);

        String accessToken = jwtUtil.generate(registeredUser.getEmail(), registeredUser.getRole(), "ACCESS");
        String refreshToken = jwtUtil.generate(registeredUser.getEmail(), registeredUser.getRole(), "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse login(LoginRequest request) {
        //do validation if user exists in DB
        UserVO reqUser = restTemplate.getForObject("http://users-service/users/{email}",
                UserVO.class,
                request.getEmail());

        if (reqUser == null) throw new IllegalArgumentException("User doesn't exist");

        BCrypt.checkpw(request.getPassword(), reqUser.getPassword());
        String jwtToken = jwtUtil.generate(reqUser.getEmail(), reqUser.getRole(), "BEARER");
        String refreshToken = jwtUtil.generate(reqUser.getEmail(), reqUser.getRole(), "REFRESH");

        // TODO: save token in the db

        return new AuthResponse(jwtToken, refreshToken);
    }

//    public String logout(AuthRequestDTO request) {
//        User loggedUser = new User();
//        loggedUser.setUsername(request.getUsername());
//        loggedUser.setPassword(request.getPassword());
//        revokeAllUserTokens(loggedUser);
//        return "logged out";
//    }

//    private void saveUserToken(User user, String jwtToken) {
//        Token token = new Token(jwtToken, TokenType.BEARER, false, false, user);
//
//        tokenRepository.save(token);
//    }
}
