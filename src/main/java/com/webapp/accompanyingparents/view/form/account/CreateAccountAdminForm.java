package com.webapp.accompanyingparents.view.form.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@ApiModel
public class CreateAccountAdminForm {
    @NotEmpty(message = "userEmail can not be null")
    @ApiModelProperty(name = "userEmail", required = true)
    @Email
    private String userEmail;
    @NotEmpty(message = "userPassword can not be null")
    @ApiModelProperty(name = "userPassword", required = true)
    private String userPassword;
    @NotEmpty(message = "userFullName can not be null")
    @ApiModelProperty(name = "userFullName", example = "Toan Huynh Thanh Nguyen", required = true)
    private String userFullName;
    @ApiModelProperty(name = "userAvatar")
    private String userAvatar;
}