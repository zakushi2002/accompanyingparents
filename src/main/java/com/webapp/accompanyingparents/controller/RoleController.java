package com.webapp.accompanyingparents.controller;

import com.webapp.accompanyingparents.model.Permission;
import com.webapp.accompanyingparents.model.Role;
import com.webapp.accompanyingparents.view.mapper.RoleMapper;
import com.webapp.accompanyingparents.model.repository.PermissionRepository;
import com.webapp.accompanyingparents.model.repository.RoleRepository;
import com.webapp.accompanyingparents.view.dto.ApiMessageDto;
import com.webapp.accompanyingparents.view.dto.ResponseListDto;
import com.webapp.accompanyingparents.view.dto.role.RoleDto;
import com.webapp.accompanyingparents.view.form.role.CreateRoleForm;
import com.webapp.accompanyingparents.view.form.role.UpdateRoleForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/role")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@ApiIgnore
public class RoleController extends ABasicController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PermissionRepository permissionRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission('ROLE', 'C')")
    @Transactional
    public ApiMessageDto<String> create(@Valid @RequestBody CreateRoleForm createRoleForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Role role = roleRepository.findFirstByName(createRoleForm.getName());
        if (role != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Role name is exist");
            return apiMessageDto;
        }

        role = new Role();
        role.setName(createRoleForm.getName());
        role.setDescription(createRoleForm.getDescription());
        List<Permission> permissions = new ArrayList<>();
        for (int i = 0; i < createRoleForm.getPermissions().length; i++) {
            Permission permission = permissionRepository.findById(createRoleForm.getPermissions()[i]).orElse(null);
            if (permission != null) {
                permissions.add(permission);
            }
        }
        String email = getCurrentUser();
        role.setCreatedBy(email);
        role.setModifiedBy(email);
        role.setStatus(1);
        role.setPermissions(permissions);
        roleRepository.save(role);
        apiMessageDto.setMessage("Create role success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission('ROLE', 'U')")
    @Transactional
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateRoleForm updateRoleForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Role role = roleRepository.findById(updateRoleForm.getId()).orElse(null);
        if (role == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Role name doesnt exist");
            return apiMessageDto;
        }
        //check su ton tai cua role name khac khi dat ten.
        Role otherGroup = roleRepository.findFirstByName(updateRoleForm.getName());
        if (otherGroup != null && !Objects.equals(updateRoleForm.getId(), otherGroup.getId())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Cant update this role name because it is exist");
            return apiMessageDto;
        }
        role.setName(updateRoleForm.getName());
        role.setDescription(updateRoleForm.getDescription());
        List<Permission> permissions = new ArrayList<>();
        for (int i = 0; i < updateRoleForm.getPermissions().length; i++) {
            Permission permission = permissionRepository.findById(updateRoleForm.getPermissions()[i]).orElse(null);
            if (permission != null) {
                permissions.add(permission);
            }
        }
        role.setPermissions(permissions);
        roleRepository.save(role);
        apiMessageDto.setMessage("Update role success");

        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission('ROLE', 'V')")
    public ApiMessageDto<Role> get(@PathVariable("id") Long id) {
        ApiMessageDto<Role> apiMessageDto = new ApiMessageDto<>();
        Role role = roleRepository.findById(id).orElse(null);
        apiMessageDto.setData(role);
        apiMessageDto.setMessage("Get role success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission('ROLE', 'L')")
    public ApiMessageDto<ResponseListDto<RoleDto>> list(@RequestParam(required = true) int page, @RequestParam(required = true) int size) {

        ApiMessageDto<ResponseListDto<RoleDto>> apiMessageDto = new ApiMessageDto<>();

        Page<Role> groups = roleRepository
                .findAll(PageRequest.of(page, size, Sort.by(new Sort.Order(Sort.Direction.DESC, "createdDate"))));

        ResponseListDto<RoleDto> responseListDto = new ResponseListDto(roleMapper.fromEntitiesToRoleDtoList(groups.getContent()), groups.getNumber(), groups.getTotalElements(), groups.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("list role success");
        return apiMessageDto;
    }
}