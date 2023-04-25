package com.webapp.accompanyingparents.view.form.token;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateAuthenticationTokenForm {
    @NotNull(message = "id can not be null")
    @ApiModelProperty(name = "id", required = true)
    private Long id;

    @ApiModelProperty(name = "status", required = false)
    private Integer status;

    @NotNull(message = "name can not be null")
    @ApiModelProperty(name = "name", required = false)
    private String name;
}