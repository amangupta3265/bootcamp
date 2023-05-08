package com.group6.bootcampplayground.repository;

import com.group6.bootcampplayground.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, String> {
    Otp findByEmail(String email);
}
