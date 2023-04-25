package com.webapp.accompanyingparents.view.form.permission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class CreatePermissionForm {
    @NotEmpty(message = "name can not be null")
    @ApiModelProperty(name = "name", required = true)
    private String name;
    @NotEmpty(message = "action can not be null")
    @ApiModelProperty(name = "action", required = true)
    private String action;
    @NotNull(message = "showMenu can not be null")
    @ApiModelProperty(name = "showMenu", required = true)
    private Boolean showMenu;
    @NotEmpty(message = "description can not be null")
    @ApiModelProperty(name = "description", required = true)
    private String description;
    @NotEmpty(message = "nameGroup can not be null")
    @ApiModelProperty(name = "nameGroup", required = true)
    private String nameGroup;
    @NotEmpty(message = "permissionCode can not be null")
    @ApiModelProperty(name = "permissionCode", required = true)
    private String permissionCode;
}