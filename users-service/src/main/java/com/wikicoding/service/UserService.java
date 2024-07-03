package com.wikicoding.service;

import com.wikicoding.entity.UserVO;
import com.wikicoding.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserVO createUser(UserVO userVO) {
        Optional<UserVO> user = userRepository.findByEmail(userVO.getEmail());

        if (user.isPresent()) throw new IllegalArgumentException("Username already exists");

        return userRepository.save(userVO);
    }

    public Optional<UserVO> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
