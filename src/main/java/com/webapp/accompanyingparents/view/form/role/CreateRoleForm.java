package com.webapp.accompanyingparents.view.form.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class CreateRoleForm {
    @NotEmpty(message = "Name can not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;
    @NotEmpty(message = "description can not be null")
    @ApiModelProperty(name = "description", required = true)
    private String description;
    @NotNull(message = "permissions can not be null")
    @ApiModelProperty(name = "permissions", required = true)
    private Long[] permissions;
}