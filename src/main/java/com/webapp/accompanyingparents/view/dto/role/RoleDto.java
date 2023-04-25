package com.webapp.accompanyingparents.view.dto.role;

import com.webapp.accompanyingparents.view.dto.permission.PermissionDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RoleDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "name")
    private String name;
    @ApiModelProperty(name = "description")
    private String description;
    @ApiModelProperty(name = "permissions")
    private List<PermissionDto> permissionDtos;
}