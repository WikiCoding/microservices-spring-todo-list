package com.wikicoding.jwt_auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class SecureController {
    @GetMapping
    public String getData() {
        return "Data from secured endpoint";
    }
}
