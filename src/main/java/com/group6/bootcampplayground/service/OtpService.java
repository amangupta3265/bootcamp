package com.group6.bootcampplayground.service;

import com.group6.bootcampplayground.model.Otp;
import com.group6.bootcampplayground.repository.OtpRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OtpService {

    private final OtpRepository repository;

    public String getNewOtp() {
        return getAlphaNumericString(4);
    }

    public boolean verifyUser() {
        return false;
    }

    public String getAlphaNumericString(int n){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public void saveOtpRecord(Otp otp) {
        repository.save(otp);
    }

    public String getOtpByEmail(String useremail) {
        return repository.findByEmail(useremail).getOtp();
    }

}
