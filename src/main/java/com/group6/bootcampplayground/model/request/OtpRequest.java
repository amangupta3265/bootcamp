package com.group6.bootcampplayground.model.request;

import lombok.Data;

@Data
public class OtpRequest {
    private String otp;

    private String newPassword;


}
