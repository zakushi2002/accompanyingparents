package com.webapp.accompanyingparents.view.mapper;

import com.webapp.accompanyingparents.model.Role;
import com.webapp.accompanyingparents.view.dto.role.RoleDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "permissions", target = "permissionDtos")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToRoleDto")
    RoleDto fromEntityToRoleDto(Role role);

    @IterableMapping(elementTargetType = RoleDto.class, qualifiedByName = "fromEntityToRoleDto")
    List<RoleDto> fromEntitiesToRoleDtoList(List<Role> roles);
}