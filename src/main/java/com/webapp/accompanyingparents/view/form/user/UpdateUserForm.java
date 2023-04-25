package com.webapp.accompanyingparents.view.form.user;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@ApiModel
public class UpdateUserForm {
    @Email
    @NotEmpty(message = "userEmail can not be null")
    @Size(min = 6, message = "userEmail minimum leng 6")
    @ApiModelProperty(name = "userEmail", required = true)
    private String userEmail;

    @NotEmpty(message = "password can not be null")
    @Size(min = 8, message = "password minimum leng 8")
    @ApiModelProperty(name = "userPassword", required = true)
    private String userPassword;

    @Size(min = 8, message = "userNewPassword minimum leng 8")
    @ApiModelProperty(name = "userNewPassword", required = true)
    private String userNewPassword;

    @NotEmpty(message = "userName can not be null")
    @Size(min = 6, message = "userName minimum leng 6")
    @ApiModelProperty(name = "userName", required = true)
    private String userName;

    @ApiModelProperty(name = "userAvatar", required = false)
    private String userAvatar;

    @ApiModelProperty(name = "userDayOfBirth", required = true)
    private Date userDayOfBirth;

    @ApiModelProperty(name = "userPhone", required = false)
    private String userPhone;
}