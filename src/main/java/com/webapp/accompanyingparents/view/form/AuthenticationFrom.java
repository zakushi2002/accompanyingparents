package com.webapp.accompanyingparents.view.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AuthenticationFrom {
    @NotNull(message = "email can be not null")
    private String email;
    @NotNull(message = "password can be not null")
    @Size(min = 8)
    private String password;
}