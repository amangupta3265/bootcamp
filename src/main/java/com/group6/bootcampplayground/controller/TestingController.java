package com.group6.bootcampplayground.controller;

import com.group6.bootcampplayground.auth.JWTService;
import com.group6.bootcampplayground.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestingController {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    @GetMapping("/test")
    public ResponseEntity<String> testFunction(@RequestHeader("Authorization") String header) {
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        return ResponseEntity.ok().body(userRepository.findById("apoorv@gmail.com").toString());
    }
}
