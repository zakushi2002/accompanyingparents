package com.webapp.accompanyingparents.view.form.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@ApiModel
public class CreateAccountAdminForm {
    @NotEmpty(message = "email can not be null")
    @ApiModelProperty(name = "email", required = true)
    @Email
    private String email;
    @NotEmpty(message = "password can not be null")
    @ApiModelProperty(name = "password", required = true)
    private String password;
    @NotEmpty(message = "fullName can not be null")
    @ApiModelProperty(name = "fullName", example = "Toan Huynh Thanh Nguyen", required = true)
    private String fullName;
    private String avatarPath;
}