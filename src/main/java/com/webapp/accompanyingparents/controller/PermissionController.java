package com.webapp.accompanyingparents.controller;

import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.Permission;
import com.webapp.accompanyingparents.model.repository.AccountRepository;
import com.webapp.accompanyingparents.model.repository.PermissionRepository;
import com.webapp.accompanyingparents.view.dto.ApiMessageDto;
import com.webapp.accompanyingparents.view.dto.ErrorCode;
import com.webapp.accompanyingparents.view.form.permission.CreatePermissionForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/v1/permission")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@ApiIgnore
public class PermissionController extends ABasicController {
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    AccountRepository accountRepository;

    @PreAuthorize("hasPermission('PERMISSION', 'C')")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@RequestBody CreatePermissionForm createPermissionForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        Permission permission = permissionRepository.findFirstByName(createPermissionForm.getName());
        if (permission != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Permission name is exist");
            return apiMessageDto;
        }
        String email = getCurrentUser();
        Account account = accountRepository.findAccountByEmail(email);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        permission = new Permission();
        permission.setName(createPermissionForm.getName());
        permission.setAction(createPermissionForm.getAction());
        permission.setDescription(createPermissionForm.getDescription());
        permission.setShowMenu(createPermissionForm.getShowMenu());
        permission.setNameGroup(createPermissionForm.getNameGroup());
        permission.setPCode(createPermissionForm.getPermissionCode());
        permission.setCreatedBy(account.getEmail());
        permission.setModifiedBy(account.getEmail());
        permissionRepository.save(permission);
        apiMessageDto.setMessage("Create permission success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission('PERMISSION', 'L')")
    public ApiMessageDto<List<Permission>> list() {
        ApiMessageDto<List<Permission>> apiMessageDto = new ApiMessageDto<>();
        Page<Permission> accounts = permissionRepository.findAll(PageRequest.of(0, 1000, Sort.by(new Sort.Order(Sort.Direction.DESC, "createdDate"))));
        apiMessageDto.setData(accounts.getContent());
        apiMessageDto.setMessage("Get permissions list success");
        return apiMessageDto;
    }
}