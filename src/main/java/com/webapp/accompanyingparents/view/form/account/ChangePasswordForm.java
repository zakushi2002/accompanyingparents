package com.webapp.accompanyingparents.view.form.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordForm {
    @NotEmpty(message = "newPassword can not be null")
    @Size(min = 8, message = "newPassword minimum 8 character.")
    @ApiModelProperty(name = "newPassword", required = true)
    private String newPassword;
}