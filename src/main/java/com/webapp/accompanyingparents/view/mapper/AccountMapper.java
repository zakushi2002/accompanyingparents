package com.webapp.accompanyingparents.view.mapper;

import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.view.dto.account.AccountDto;
import com.webapp.accompanyingparents.view.form.account.ChangePasswordForm;
import com.webapp.accompanyingparents.view.form.user.ChangePasswordForgotForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {RoleMapper.class})
public interface AccountMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "role", target = "roleDto")
    @Mapping(source = "lastLogin", target = "lastLogin")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromAccountCMSToDto")
    AccountDto fromAccountCMSToDto(Account account);

    @Mapping(source = "newPassword", target = "password")
    @BeanMapping(ignoreByDefault = true)
    void mappingForChangePassword(ChangePasswordForm changePasswordForm, @MappingTarget Account account);

    @IterableMapping(elementTargetType = AccountDto.class, qualifiedByName = "fromAccountCMSToDto")
    List<AccountDto> fromEntitiesToAccountCMSDtoList(List<Account> list);
    @Mapping(source = "newPassword", target = "password")
    @Mapping(source = "email", target = "email")
    @BeanMapping(ignoreByDefault = true)
    void mappingForgotChangePassword(ChangePasswordForgotForm changePasswordForgotForm, @MappingTarget Account account);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "role", target = "roleDto", qualifiedByName = "fromEntityCMSToRoleDto")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromAccountForPostToDto")
    AccountDto fromAccountForPostToDto(Account account);
}