package com.webapp.accompanyingparents.view.mapper;

import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.view.dto.account.AccountAutoCompleteDto;
import com.webapp.accompanyingparents.view.dto.account.AccountCMSDto;
import com.webapp.accompanyingparents.view.form.user.ChangePasswordForm;
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
    AccountCMSDto fromAccountCMSToDto(Account account);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @Mapping(source = "fullName", target = "fullName")
    AccountAutoCompleteDto fromAccountToAutoCompleteDto(Account account);

    @IterableMapping(elementTargetType = AccountAutoCompleteDto.class)
    List<AccountAutoCompleteDto> convertAccountToAutoCompleteDto(List<Account> list);

    @Mapping(source = "newPassword", target = "password")
    @BeanMapping(ignoreByDefault = true)
    void mappingForChangePassword(ChangePasswordForm changePasswordForm, @MappingTarget Account account);

    @IterableMapping(elementTargetType = AccountCMSDto.class, qualifiedByName = "fromAccountCMSToDto")
    List<AccountCMSDto> fromEntitiesToAccountCMSDtoList(List<Account> list);
}