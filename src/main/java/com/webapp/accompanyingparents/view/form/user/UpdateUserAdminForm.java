package com.webapp.accompanyingparents.view.form.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateUserAdminForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @ApiModelProperty(name = "status", required = false)
    private Integer status;

    @Size(min = 8, message = "userNewPassword minimum leng 8")
    @ApiModelProperty(name = "userNewPassword", required = true)
    private String userNewPassword;
}