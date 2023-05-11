package com.webapp.accompanyingparents.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class APService {
    @Autowired
    OTPService otpService;
    @Autowired
    CommonAsyncService commonAsyncService;

    public String getOTPForgetPassword() {
        return otpService.generate(6);
    }
}