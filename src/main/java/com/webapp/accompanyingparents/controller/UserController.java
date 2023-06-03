package com.webapp.accompanyingparents.controller;

import com.webapp.accompanyingparents.config.constant.APConstant;
import com.webapp.accompanyingparents.model.FileData;
import com.webapp.accompanyingparents.model.repository.FileDataRepository;
import com.webapp.accompanyingparents.service.APService;
import com.webapp.accompanyingparents.service.CommonAsyncService;
import com.webapp.accompanyingparents.service.FileStorageService;
import com.webapp.accompanyingparents.service.factorymethod.AccountFactory;
import com.webapp.accompanyingparents.service.factorymethod.AccountType;
import com.webapp.accompanyingparents.service.factorymethod.IAccount;
import com.webapp.accompanyingparents.view.form.account.GetOTPForm;
import com.webapp.accompanyingparents.view.form.account.OTPForm;
import com.webapp.accompanyingparents.view.form.user.ChangePasswordForgotForm;
import com.webapp.accompanyingparents.view.form.user.UpdateUserProfileForm;
import com.webapp.accompanyingparents.view.mapper.AccountMapper;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Date;

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
    @Autowired
    CommonAsyncService commonAsyncService;
    @Autowired
    APService apService;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    FileDataRepository fileDataRepository;
    IAccount iAccount;

    /**
     * Đăng ký tài khoản USER
     */
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> save(@RequestBody @Valid CreateUserForm signUp, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(signUp.getUserEmail());
        System.out.println(accountRepository);
        if (account != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_EXIST);
            return apiMessageDto;
        }

        // Tạo account for USER
        // Applying Factory Method Pattern
        iAccount = AccountFactory.newAccount(AccountType.USER);
        account = iAccount.create();
        Role role = roleRepository.findFirstByName(APConstant.ROLE_END_USER.trim());
        if (!account.getRole().getId().equals(role.getId())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_UNKNOWN);
            return apiMessageDto;
        }

        account.setEmail(signUp.getUserEmail());
        account.setFullName(signUp.getUserFullName());
        account.setAvatarPath(signUp.getUserAvatar());
        account.setStatus(APConstant.STATUS_ACTIVE);
        account.setPassword(passwordEncoder.encode(signUp.getUserPassword()));
        account.setCreatedBy(account.getEmail());
        account.setModifiedBy(account.getEmail());
        accountRepository.save(account);

        // Tạo user profile
        UserProfile user = userProfileMapper.formCreateUserProfile(signUp);
        user.setStatus(APConstant.STATUS_ACTIVE);
        user.setAccount(account);
        user.setCreatedBy(account.getEmail());
        user.setModifiedBy(account.getEmail());
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
    @Transactional
    public ApiMessageDto<Long> createExpert(@RequestBody @Valid CreateUserForm signUp, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(signUp.getUserEmail());
        if (account != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_EXIST);
            return apiMessageDto;
        }

        // Tạo account for USER
        // Applying Factory Method Pattern
        iAccount = AccountFactory.newAccount(AccountType.EXPERT);
        account = iAccount.create();
        Role role = roleRepository.findFirstByName(APConstant.ROLE_EXPERT.trim());
        if (!account.getRole().getId().equals(role.getId())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_UNKNOWN);
            return apiMessageDto;
        }

        account.setEmail(signUp.getUserEmail());
        account.setFullName(signUp.getUserFullName());
        account.setAvatarPath(signUp.getUserAvatar());
        account.setStatus(APConstant.STATUS_ACTIVE);
        account.setPassword(passwordEncoder.encode(signUp.getUserPassword()));
        String email = getCurrentUser();
        account.setCreatedBy(email);
        account.setModifiedBy(email);
        accountRepository.save(account);

        // Tạo expert profile
        UserProfile user = userProfileMapper.formCreateUserProfile(signUp);
        user.setStatus(APConstant.STATUS_ACTIVE);
        user.setAccount(account);
        user.setCreatedBy(email);
        user.setModifiedBy(email);
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

    @PutMapping(value = "/send-otp-code", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> sendOTPCode(@Valid @RequestBody GetOTPForm getOTPForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(getOTPForm.getEmail());
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        String otp = apService.getOTPForgetPassword();
        account.setResetPwdCode(otp);
        account.setResetPwdTime(new Date());
        accountRepository.save(account);
        String message = "<p>Hello " + account.getFullName() + ",</p>"
                + "<p>For forgot password, you're required to use the following One-Time Password (OTP) to change password:</p>"
                + "<p><b>" + otp + "</b></p>"
                + "<br>"
                + "<p>Note: this OTP is set to expire in 5 minutes.</p>";
        commonAsyncService.sendEmail(getOTPForm.getEmail(), message, APConstant.OTP_SUBJECT_EMAIL);
        apiMessageDto.setMessage("Send OTP successfully");
        return apiMessageDto;
    }

    @PutMapping(value = "/check-otp-code", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> checkOTPCode(@Valid @RequestBody OTPForm otp) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByResetPwdCode(otp.getOtp());
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_OTP_INVALID);
            return apiMessageDto;
        }
        if (!account.isOTPRequired()) {
            account.setResetPwdCode(null);
            account.setResetPwdTime(null);
            accountRepository.save(account);
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_OTP_EXPIRED);
            return apiMessageDto;
        }
        account.setResetPwdCode(null);
        account.setResetPwdTime(null);
        accountRepository.save(account);
        apiMessageDto.setMessage("Correct OTP code");
        return apiMessageDto;
    }

    @PutMapping(value = "/change-password", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> changePassword(@Valid @RequestBody ChangePasswordForgotForm changePasswordForgotForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(changePasswordForgotForm.getEmail());
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        accountMapper.mappingForgotChangePassword(changePasswordForgotForm, account);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        apiMessageDto.setMessage("Change password success");
        return apiMessageDto;
    }

    @PostMapping(value = "/uploadFile", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<FileData> uploadFile(@RequestParam("file") MultipartFile file) {
        ApiMessageDto<FileData> apiMessageDto = new ApiMessageDto<>();
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        FileData fileData = new FileData(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
        fileData.setCreatedBy(getCurrentUser());
        fileData.setModifiedBy(getCurrentUser());
        fileDataRepository.save(fileData);
        apiMessageDto.setData(fileData);
        apiMessageDto.setMessage("Upload file success");
        return apiMessageDto;
    }
}