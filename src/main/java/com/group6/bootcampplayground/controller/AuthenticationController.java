package com.group6.bootcampplayground.controller;


import com.group6.bootcampplayground.model.Otp;
import com.group6.bootcampplayground.model.request.OTPEmailRequest;
import com.group6.bootcampplayground.model.request.AuthenticationRequest;
import com.group6.bootcampplayground.model.request.RegisterRequest;
import com.group6.bootcampplayground.model.response.AuthenticationResponse;
import com.group6.bootcampplayground.service.AuthenticationService;
import com.group6.bootcampplayground.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private OtpService otpService;


    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }





}
