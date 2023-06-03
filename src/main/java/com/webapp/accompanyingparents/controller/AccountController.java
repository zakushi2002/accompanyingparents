package com.webapp.accompanyingparents.controller;

import com.webapp.accompanyingparents.config.constant.APConstant;
import com.webapp.accompanyingparents.model.*;
import com.webapp.accompanyingparents.model.criteria.AccountCriteria;
import com.webapp.accompanyingparents.model.repository.*;
import com.webapp.accompanyingparents.service.factorymethod.AccountFactory;
import com.webapp.accompanyingparents.service.factorymethod.AccountType;
import com.webapp.accompanyingparents.service.factorymethod.IAccount;
import com.webapp.accompanyingparents.view.dto.ApiMessageDto;
import com.webapp.accompanyingparents.view.dto.ApiResponse;
import com.webapp.accompanyingparents.view.dto.ErrorCode;
import com.webapp.accompanyingparents.view.dto.ResponseListDto;
import com.webapp.accompanyingparents.view.dto.account.AccountDto;
import com.webapp.accompanyingparents.view.form.account.CreateAccountAdminForm;
import com.webapp.accompanyingparents.view.form.account.UpdateAccountAdminForm;
import com.webapp.accompanyingparents.view.form.account.ChangePasswordForm;
import com.webapp.accompanyingparents.view.mapper.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    IAccount iAccount;

    @PreAuthorize("hasPermission('ADMIN', 'C')")
    @PostMapping(value = "/create-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<String> createSuperAdmin(@Valid @RequestBody CreateAccountAdminForm createAccountAdminForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(createAccountAdminForm.getUserEmail());
        if (account != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_EMAIL_EXIST);
            return apiMessageDto;
        }

        // Applying Factory Method Pattern
        iAccount = AccountFactory.newAccount(AccountType.ADMIN);
        account = iAccount.create();
        Role role = roleRepository.findFirstByName(APConstant.ROLE_ADMIN);
        System.out.println(account.getRole().getId() + " " + role.getId());
        if (!account.getRole().getId().equals(role.getId())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_UNKNOWN);
            return apiMessageDto;
        }

        account.setEmail(createAccountAdminForm.getUserEmail());
        account.setPassword(passwordEncoder.encode(createAccountAdminForm.getUserPassword()));
        account.setFullName(createAccountAdminForm.getUserFullName());
        account.setStatus(APConstant.STATUS_ACTIVE);
        account.setAvatarPath(createAccountAdminForm.getUserAvatar());
        String email = getCurrentUser();
        account.setCreatedBy(email);
        account.setModifiedBy(email);
        accountRepository.save(account);

        apiMessageDto.setMessage("Create account admin success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission('ADMIN', 'U')")
    @Transactional
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

        apiMessageDto.setMessage("Update account admin success");
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
    @Transactional
    public ApiMessageDto<String> deleteAccount(@PathVariable("id") Long accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (!account.getRole().getName().trim().equals(APConstant.ROLE_SUPER_ADMIN) && !account.getRole().getName().trim().equals(APConstant.ROLE_ADMIN)) {
            UserProfile userProfile = userProfileRepository.findUserProfileByAccountEmail(account.getEmail());
            if (userProfile == null) {
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
                return apiMessageDto;
            }
            userProfileRepository.deleteById(userProfile.getId());
            List<Comment> comments = commentRepository.findCommentsByAccount(account);
            if (!comments.isEmpty()) {
                commentRepository.deleteAllByAccount(account);
                /*for (Comment c : comments) {
                    commentRepository.deleteById(c.getId());
                }*/
            }
            List<Bookmark> bookmarks = bookmarkRepository.findAllByAccount(account);
            if (!bookmarks.isEmpty()) {
                bookmarkRepository.deleteAllByAccount(account);
                /*for (Bookmark b : bookmarks) {
                    bookmarkRepository.deleteById(b.getId());
                }*/
            }
            List<Post> posts = postRepository.findAllByAccount(account);
            if (!posts.isEmpty()) {
                for (Post p: posts) {
                    List<Comment> commentsByPost = commentRepository.findCommentsByPostId(p.getId());
                    if (!commentsByPost.isEmpty()) {
                        commentRepository.deleteAllByPostId(p.getId());
                        /*for (Comment c : commentsByPost) {
                            commentRepository.deleteById(c.getId());
                        }*/
                    }
                    postRepository.deleteById(p.getId());
                }
            }

        }
        accountRepository.deleteById(accountId);
        apiMessageDto.setMessage("Delete Account success");
        return apiMessageDto;
    }

    @PreAuthorize("hasPermission('PASSWORD', 'U')")
    @PutMapping(value = "/change-password")
    @Transactional
    public ApiMessageDto<Long> changePassword(@Valid @RequestBody ChangePasswordForm changePasswordForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        String email = getCurrentUser();
        Account account = accountRepository.findAccountByEmail(email);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        accountMapper.mappingForChangePassword(changePasswordForm, account);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        apiMessageDto.setMessage("Change password success");
        return apiMessageDto;
    }

    @PreAuthorize("hasPermission('ACCOUNT', 'L')")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<AccountDto>> listAccountCMS(AccountCriteria accountCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<AccountDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Account> page = accountRepository.findAll(accountCriteria.getSpecification(), pageable);
        ResponseListDto<AccountDto> responseListDto = new ResponseListDto(accountMapper.fromEntitiesToAccountCMSDtoList(page.getContent()), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get account list success");
        return apiMessageDto;
    }
}