package com.webapp.accompanyingparents.view.form.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@ApiModel
public class CreateUserForm {
    @Email
    @NotEmpty(message = "userEmail can not be null")
    @Size(min = 6, message = "userEmail minimum length 6")
    @ApiModelProperty(name = "userEmail", required = true)
    private String userEmail;

    @NotEmpty(message = "userPassword can not be empty")
    @NotNull(message = "userPassword can not be null")
    @Size(min = 8, message = "userPassword minimum length 8")
    @ApiModelProperty(name = "userPassword", required = true)
    private String userPassword;

    @NotEmpty(message = "userFullName can not be empty")
    @NotNull(message = "userFullName can not be null")
    @Size(min = 2, message = "userFullName minimum length 2")
    @ApiModelProperty(name = "userFullName", required = true)
    private String userFullName;

    @ApiModelProperty(name = "userAvatar", required = false)
    private String userAvatar;

    @ApiModelProperty(name = "userDayOfBirth", required = true)
    @NotNull(message = "userDayOfBirth can not be null")
    private Date userDayOfBirth;

    @NotEmpty(message = "userPhone can not be empty")
    @ApiModelProperty(name = "userPhone", required = true)
    private String userPhone;
}