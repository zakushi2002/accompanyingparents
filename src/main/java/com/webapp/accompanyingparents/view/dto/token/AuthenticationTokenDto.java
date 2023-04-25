package com.webapp.accompanyingparents.view.dto.token;

import lombok.Data;

import java.util.Date;

@Data
public class AuthenticationTokenDto {
    private String token;
    private Date tokenExpiredDate;
    private String email;
    private String role;
}