package com.webapp.accompanyingparents.controller;

import com.webapp.accompanyingparents.config.constant.APConstant;
import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.Role;
import com.webapp.accompanyingparents.model.UserProfile;
import com.webapp.accompanyingparents.model.repository.AccountRepository;
import com.webapp.accompanyingparents.model.repository.RoleRepository;
import com.webapp.accompanyingparents.model.repository.UserProfileRepository;
import com.webapp.accompanyingparents.view.dto.ApiMessageDto;
import com.webapp.accompanyingparents.view.dto.ApiResponse;
import com.webapp.accompanyingparents.view.dto.ErrorCode;
import com.webapp.accompanyingparents.view.form.account.CreateAccountAdminForm;
import com.webapp.accompanyingparents.view.form.account.UpdateAccountAdminForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class AccountController extends ABasicController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserProfileRepository userProfileRepository;

    @PreAuthorize("hasPermission('ADMIN', 'C')")
    @PostMapping(value = "/create-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> createSuperAdmin(@Valid @RequestBody CreateAccountAdminForm createAccountAdminForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(createAccountAdminForm.getEmail());
        if (account != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_EMAIL_EXIST);
            return apiMessageDto;
        }
        Role role = roleRepository.findFirstByName(createAccountAdminForm.getRoleName());
        if (role == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_UNKNOWN);
            return apiMessageDto;
        }
        account = new Account();
        account.setEmail(createAccountAdminForm.getEmail());
        account.setPassword(passwordEncoder.encode(createAccountAdminForm.getPassword()));
        account.setFullName(createAccountAdminForm.getFullName());
        account.setRole(role);
        account.setStatus(createAccountAdminForm.getStatus());
        account.setAvatarPath(createAccountAdminForm.getAvatarPath());
        account.setIsSuperAdmin(false);
        account.setCreatedBy(account.getEmail());
        account.setModifiedBy(account.getEmail());
        accountRepository.save(account);
        apiMessageDto.setMessage("Create account super admin success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission('ADMIN', 'U')")
    public ApiResponse<String> updateAdmin(@Valid @RequestBody UpdateAccountAdminForm updateAccountAdminForm, BindingResult bindingResult) {

        ApiResponse<String> apiMessageDto = new ApiResponse<>();
        Account account = accountRepository.findById(updateAccountAdminForm.getId()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Role role = roleRepository.findFirstByName(APConstant.ROLE_ADMIN);
        if (role == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_UNKNOWN);
            return apiMessageDto;
        }
        if (updateAccountAdminForm.getPassword() != null) {
            account.setPassword(passwordEncoder.encode(updateAccountAdminForm.getPassword()));
        }
        account.setFullName(updateAccountAdminForm.getFullName());
        if (updateAccountAdminForm.getAvatarPath() != null) {
            if (!updateAccountAdminForm.getAvatarPath().equals(account.getAvatarPath())) {
                //delete old image
                account.setAvatarPath(updateAccountAdminForm.getAvatarPath());
            }
        }
        account.setStatus(updateAccountAdminForm.getStatus());
        accountRepository.save(account);

        apiMessageDto.setMessage("Update account super admin success");
        return apiMessageDto;
    }

    @PreAuthorize("hasPermission('ADMIN', 'D')")
    @DeleteMapping(value = "/delete-status/{id}")
    public ApiMessageDto<String> deleteAccountStatus(@PathVariable("id") Long accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (!account.getRole().getName().trim().equals(APConstant.ROLE_SUPER_ADMIN)) {
            UserProfile userProfile = userProfileRepository.findUserProfileByAccountEmail(account.getEmail());
            if (userProfile == null) {
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
                return apiMessageDto;
            }
            userProfile.setStatus(APConstant.STATUS_DELETE);
            userProfileRepository.save(userProfile);
        }
        account.setStatus(APConstant.STATUS_DELETE);
        accountRepository.save(account);
        apiMessageDto.setMessage("Delete Account success");
        return apiMessageDto;
    }

    @PreAuthorize("hasPermission('ADMIN', 'D')")
    @DeleteMapping(value = "/delete/{id}")
    public ApiMessageDto<String> deleteAccount(@PathVariable("id") Long accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (!account.getRole().getName().trim().equals(APConstant.ROLE_SUPER_ADMIN)) {
            UserProfile userProfile = userProfileRepository.findUserProfileByAccountEmail(account.getEmail());
            if (userProfile == null) {
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
                return apiMessageDto;
            }
            userProfileRepository.deleteById(userProfile.getId());
        }
        accountRepository.deleteById(accountId);
        apiMessageDto.setMessage("Delete Account success");
        return apiMessageDto;
    }

    /*@GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }*/
}