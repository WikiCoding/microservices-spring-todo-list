package com.wikicoding.controller;

import com.wikicoding.entity.UserVO;
import com.wikicoding.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserVO> save(@RequestBody UserVO userVO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(userVO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserVO> findUserByEmail(@PathVariable("email") String email) {
        Optional<UserVO> user = userService.findUserByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(null));
    }

}
