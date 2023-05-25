package com.webapp.accompanyingparents.view.dto.role;

import com.webapp.accompanyingparents.view.dto.ABasicAdminDto;
import com.webapp.accompanyingparents.view.dto.permission.PermissionDto;
import lombok.Data;

import java.util.List;

@Data
public class RoleDto extends ABasicAdminDto {
    private String name;
    private String description;
    private List<PermissionDto> permissionDtos;
}