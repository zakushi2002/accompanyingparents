package com.webapp.accompanyingparents.view.mapper;

import com.webapp.accompanyingparents.model.UserProfile;
import com.webapp.accompanyingparents.view.dto.user.UserProfileDto;
import com.webapp.accompanyingparents.view.form.user.CreateUserForm;
import com.webapp.accompanyingparents.view.form.user.UpdateUserProfileForm;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    @Mapping(source = "userEmail", target = "account.email")
    @Mapping(source = "userPassword", target = "account.password")
    @Mapping(source = "userAvatar", target = "account.avatarPath")
    @Mapping(source = "userFullName", target = "account.fullName")
    @Mapping(source = "userDayOfBirth", target = "dob")
    @Mapping(source = "userPhone", target = "phone")
    @BeanMapping(ignoreByDefault = true)
    UserProfile formCreateUserProfile(CreateUserForm createUserForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "account.email", target = "userEmail")
    @Mapping(source = "account.avatarPath", target = "userAvatar")
    @Mapping(source = "account.fullName", target = "userFullName")
    @Mapping(source = "dob", target = "userDayOfBirth")
    @Mapping(source = "phone", target = "userPhone")
    @BeanMapping(ignoreByDefault = true)
    UserProfileDto fromEntityToUserProfileDto(UserProfile userProfile);

    @Mapping(source = "userAvatar", target = "account.avatarPath")
    @Mapping(source = "userFullName", target = "account.fullName")
    @Mapping(source = "userDayOfBirth", target = "dob")
    @Mapping(source = "userPhone", target = "phone")
    @BeanMapping(ignoreByDefault = true)
    void mappingForUpdateUserProfile(UpdateUserProfileForm updateUserProfileForm, @MappingTarget UserProfile userProfile);
}