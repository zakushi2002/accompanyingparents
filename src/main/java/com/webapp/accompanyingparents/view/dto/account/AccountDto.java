package com.webapp.accompanyingparents.view.dto.account;

import com.webapp.accompanyingparents.view.dto.ABasicAdminDto;
import com.webapp.accompanyingparents.view.dto.role.RoleDto;
import lombok.Data;

import java.util.Date;

@Data
public class AccountDto extends ABasicAdminDto {
    private String email;
    private String fullName;
    private Date lastLogin;
    private String avatarPath;
    private String resetPwdCode;
    private Date resetPwdTime;
    private Boolean isSuperAdmin;
    private RoleDto roleDto;
    private Integer attemptOTP;
}