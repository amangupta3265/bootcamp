package com.group6.bootcampplayground.service;

import com.group6.bootcampplayground.auth.JWTService;
import com.group6.bootcampplayground.auth.Role;
import com.group6.bootcampplayground.model.User;
import com.group6.bootcampplayground.model.request.OTPEmailRequest;
import com.group6.bootcampplayground.model.request.AuthenticationRequest;
import com.group6.bootcampplayground.model.request.RegisterRequest;
import com.group6.bootcampplayground.model.response.AuthenticationResponse;
import com.group6.bootcampplayground.model.response.OTPResponse;
import com.group6.bootcampplayground.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(new HashMap<>(), user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
//        System.out.println("================="+ authenticationRequest.getEmail()+' '+authenticationRequest.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        var user = repository.findById(authenticationRequest.getEmail())
                .orElseThrow();
//        System.out.println(user);
        var jwtToken = jwtService.generateToken(new HashMap<>(), user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse generateJWTForOTP(OTPEmailRequest resetPasswordUser) {
        User user = User.builder()
                .email(resetPasswordUser.getEmail())
                .role(Role.USER)
                .build();

        var jwtToken = jwtService.generateToken(new HashMap<>(), user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse updateUserPassword(String email, String newPassword, boolean validOtp){
        var userFromEmail = repository.findById(email)
                .orElseThrow();
        User user;
        if(validOtp) {
            user = User.builder()
                    .name(userFromEmail.getName())
                    .email(userFromEmail.getEmail())
                    .password(passwordEncoder.encode(newPassword))
                    .role(Role.USER)
                    .build();
        }
        else{
            user=userFromEmail;
        }
        repository.save(user);
        var jwtToken = jwtService.generateToken(new HashMap<>(), user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
