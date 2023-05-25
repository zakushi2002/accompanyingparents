package com.webapp.accompanyingparents.view.dto.permission;

import com.webapp.accompanyingparents.view.dto.ABasicAdminDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PermissionDto extends ABasicAdminDto {
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