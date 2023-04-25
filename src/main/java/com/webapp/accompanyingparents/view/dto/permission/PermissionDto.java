package com.webapp.accompanyingparents.view.dto.permission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PermissionDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "action")
    private String action;
    @ApiModelProperty(name = "showMenu")
    private Boolean showMenu;
    @ApiModelProperty(name = "description")
    private String description;
    @ApiModelProperty(name = "nameGroup")
    private String nameGroup;
    @ApiModelProperty(name = "pCode")
    private String pCode;
}