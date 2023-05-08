package com.group6.bootcampplayground.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotePinRequest {
    private String notePin;
    private String newPin;
    private String otp;
    private String toEmail;
    private String shareEmail;

}
