package com.webapp.accompanyingparents.view.dto.user;

import com.webapp.accompanyingparents.view.dto.ABasicAdminDto;
import lombok.Data;

import java.util.Date;

@Data
public class UserProfileDto extends ABasicAdminDto {
    private String userEmail;
    private String userFullName;
    private String userAvatar;
    private String userPhone;
    private Date userDayOfBirth;
}
