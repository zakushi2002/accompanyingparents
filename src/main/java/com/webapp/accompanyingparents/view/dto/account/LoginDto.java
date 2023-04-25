package com.webapp.accompanyingparents.view.dto.account;

import lombok.Data;

import java.util.Date;

@Data
public class LoginDto {
    private String email;
    private String phoneNumber;
    private String token;
    private String fullName;
    private long id;
    private Date expired;
}