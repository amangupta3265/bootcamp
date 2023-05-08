package com.group6.bootcampplayground.controller;

import com.group6.bootcampplayground.auth.JWTService;
import com.group6.bootcampplayground.model.Otp;
import com.group6.bootcampplayground.model.request.OTPEmailRequest;
import com.group6.bootcampplayground.model.request.OtpRequest;
import com.group6.bootcampplayground.model.response.AuthenticationResponse;
import com.group6.bootcampplayground.service.AuthenticationService;
import com.group6.bootcampplayground.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final AuthenticationService authenticationService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private OtpService otpService;

    private final JWTService jwtService;


    @PostMapping("/resetPassword")
    public AuthenticationResponse resetPassword(@RequestHeader("Authorization") String header,
                                                @RequestBody OtpRequest otp){

        String token = header.substring(7);
        String emailFromToken = jwtService.extractUsername(token);

        String savedOtp = otpService.getOtpByEmail(emailFromToken);

        String newPassword = otp.getNewPassword();

//        System.out.println("this'll be new password " + newPassword);

        return authenticationService.updateUserPassword(emailFromToken, newPassword, savedOtp.equals(otp.getOtp()));

    }

    @PostMapping("/otp")
    public ResponseEntity<AuthenticationResponse> sendOtp(@RequestHeader("Authorization") String header){
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        OTPEmailRequest user = new OTPEmailRequest();
        user.setEmail(email);
        String otp = otpService.getNewOtp();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ishandube@gmail.com");
        message.setTo(email);
        message.setText(otp);
        message.setSubject("notes sharing application verification");
        mailSender.send(message);
        otpService.saveOtpRecord(new Otp(otp, email));

        return ResponseEntity.ok(authenticationService.generateJWTForOTP(user));
    }

}
