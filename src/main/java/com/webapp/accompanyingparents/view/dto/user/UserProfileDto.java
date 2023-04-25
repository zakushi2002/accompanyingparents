package com.webapp.accompanyingparents.view.dto.user;

import com.webapp.accompanyingparents.model.UserProfile;
import com.webapp.accompanyingparents.view.dto.ABasicAdminDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.webapp.accompanyingparents.config.constant.APConstant.DATE_TIME_FORMAT;

@Data
public class UserProfileDto extends ABasicAdminDto {
    private final UserProfile userProfile;

    public UserProfileDto(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @ApiModelProperty(name = "userEmail", required = true)
    private String userEmail;

    @ApiModelProperty(name = "userFullName", required = true)
    private String userFullName;

    @ApiModelProperty(name = "userAvatar", required = false)
    private String userAvatar;

    @ApiModelProperty(name = "userPhone", required = false)
    private String userPhone;

    @ApiModelProperty(name = "userDob", required = false)
    private Date userDayOfBirth;

    @Override
    protected Long getId() {
        return userProfile.getId();
    }

    @Override
    protected Integer getStatus() {
        return userProfile.getStatus();
    }

    @Override
    protected LocalDateTime getCreatedDate() {
        return LocalDateTime.parse(userProfile.getCreatedDate().toString(), DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    @Override
    protected LocalDateTime getModifiedDate() {
        return LocalDateTime.parse(userProfile.getModifiedDate().toString(), DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
}
