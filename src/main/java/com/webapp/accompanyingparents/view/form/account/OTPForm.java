package com.webapp.accompanyingparents.view.form.account;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class OTPForm {
    @NotNull(message = "otp can be not null")
    @NotEmpty(message = "otp can be not empty")
    private String otp;
}