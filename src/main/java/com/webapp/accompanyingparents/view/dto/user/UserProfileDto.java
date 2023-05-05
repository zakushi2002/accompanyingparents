package com.webapp.accompanyingparents.view.dto.user;

import com.webapp.accompanyingparents.view.dto.ABasicAdminDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserProfileDto extends ABasicAdminDto {
    @ApiModelProperty(name = "userEmail")
    private String userEmail;

    @ApiModelProperty(name = "userFullName")
    private String userFullName;

    @ApiModelProperty(name = "userAvatar")
    private String userAvatar;

    @ApiModelProperty(name = "userPhone")
    private String userPhone;

    @ApiModelProperty(name = "userDob")
    private Date userDayOfBirth;
}
