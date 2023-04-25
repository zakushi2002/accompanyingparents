package com.webapp.accompanyingparents.view.form.token;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class VerifyTokenForm {
    @NotEmpty(message = "token can not be null")
    @ApiModelProperty(name = "token", required = true)
    private String token;
}