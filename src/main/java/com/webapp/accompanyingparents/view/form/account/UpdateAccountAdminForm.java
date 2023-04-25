package com.webapp.accompanyingparents.view.form.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class UpdateAccountAdminForm {

    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;
    @ApiModelProperty(name = "password")
    @NotEmpty(message = "password can not be empty")
    private String password;
    @NotEmpty(message = "fullName can not be null")
    @ApiModelProperty(name = "fullName", required = true)
    private String fullName;
    @ApiModelProperty(name = "avatarPath")
    private String avatarPath;
    @NotNull(message = "status can not be null")
    @ApiModelProperty(name = "status", required = true)
    private Integer status;
}