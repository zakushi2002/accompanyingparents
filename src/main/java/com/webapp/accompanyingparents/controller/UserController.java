package com.webapp.accompanyingparents.controller;

import com.webapp.accompanyingparents.config.constant.APConstant;
import com.webapp.accompanyingparents.view.dto.ApiResponse;
import com.webapp.accompanyingparents.view.form.user.UpdateUserProfileForm;
import com.webapp.accompanyingparents.view.mapper.UserProfileMapper;
import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.Role;
import com.webapp.accompanyingparents.model.UserProfile;
import com.webapp.accompanyingparents.model.repository.AccountRepository;
import com.webapp.accompanyingparents.model.repository.RoleRepository;
import com.webapp.accompanyingparents.model.repository.UserProfileRepository;
import com.webapp.accompanyingparents.view.dto.ApiMessageDto;
import com.webapp.accompanyingparents.view.dto.ErrorCode;
import com.webapp.accompanyingparents.view.dto.user.UserProfileDto;
import com.webapp.accompanyingparents.view.form.user.CreateUserForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class UserController extends ABasicController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    UserProfileMapper userProfileMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RoleRepository roleRepository;

    /**
     * Đăng ký tài khoản USER
     */
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> save(@RequestBody @Valid CreateUserForm signUp, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(signUp.getUserEmail());
        if (account != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_EXIST);
            return apiMessageDto;
        }
        Role role = roleRepository.findFirstByName(APConstant.ROLE_END_USER.trim());
        if (role == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_UNKNOWN);
            return apiMessageDto;
        }
        UserProfile user = userProfileMapper.formCreateUserProfile(signUp);
        user.setStatus(APConstant.STATUS_ACTIVE);

        // Tạo account for USER
        account = user.getAccount();
        account.setRole(role);
        account.setStatus(APConstant.STATUS_ACTIVE);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setCreatedBy(account.getEmail());
        account.setModifiedBy(account.getEmail());
        user.setCreatedBy(account.getEmail());
        user.setModifiedBy(account.getEmail());

        account = accountRepository.save(user.getAccount());

        // Tạo user profile
        userProfileRepository.save(user);

        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Register successfully.");
        return apiMessageDto;
    }

    @PreAuthorize("hasPermission('USER','V')")
    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<UserProfileDto> profile() {
        String email = getCurrentUser();
        ApiMessageDto<UserProfileDto> apiMessageDto = new ApiMessageDto<>();
        UserProfile profile = userProfileRepository.findUserProfileByAccountEmail(email);
        if (profile == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        apiMessageDto.setData(userProfileMapper.fromEntityToUserProfileDto(profile));
        apiMessageDto.setMessage("Get profile success");
        return apiMessageDto;
    }

    @PreAuthorize("hasPermission('EXPERT','C')")
    @PostMapping(value = "/create-expert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<Long> createExpert(@RequestBody @Valid CreateUserForm signUp, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(signUp.getUserEmail());
        if (account != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_EXIST);
            return apiMessageDto;
        }
        Role role = roleRepository.findFirstByName(APConstant.ROLE_EXPERT.trim());
        if (role == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_UNKNOWN);
            return apiMessageDto;
        }
        UserProfile user = userProfileMapper.formCreateUserProfile(signUp);
        user.setStatus(APConstant.STATUS_ACTIVE);

        // Tạo account for USER
        account = user.getAccount();
        account.setRole(role);
        account.setStatus(APConstant.STATUS_ACTIVE);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        String email = getCurrentUser();
        account.setCreatedBy(email);
        account.setModifiedBy(email);
        user.setCreatedBy(email);
        user.setModifiedBy(email);

        account = accountRepository.save(user.getAccount());

        // Tạo expert profile
        userProfileRepository.save(user);

        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Create account for Expert successfully.");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission('USER', 'U')")
    @Transactional
    public ApiMessageDto<Long> updateUserProfile(@Valid @RequestBody UpdateUserProfileForm updateUserProfileForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        String email = getCurrentUser();
        Account account = accountRepository.findAccountByEmail(email);
        UserProfile userProfile = userProfileRepository.findById(account.getId()).orElse(null);
        if (userProfile == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        userProfileMapper.mappingForUpdateUserProfile(updateUserProfileForm, userProfile);
        userProfileRepository.save(userProfile);
        apiMessageDto.setMessage("Update account success");
        return apiMessageDto;
    }
}