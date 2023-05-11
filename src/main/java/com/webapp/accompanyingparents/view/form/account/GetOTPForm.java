package com.webapp.accompanyingparents.view.form.account;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class GetOTPForm {
    @Email
    @NotNull(message = "email can be not null")
    @NotEmpty(message = "email can be not empty")
    private String email;
}