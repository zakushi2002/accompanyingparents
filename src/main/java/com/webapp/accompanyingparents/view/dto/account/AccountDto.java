package com.webapp.accompanyingparents.view.dto.account;

import com.webapp.accompanyingparents.view.dto.role.RoleDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AccountDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "email")
    private String email;
    @ApiModelProperty(name = "fullName")
    private String fullName;
    @ApiModelProperty(name = "role")
    private RoleDto roleDto;
    @ApiModelProperty(name = "lastLogin")
    private Date lastLogin;
    @ApiModelProperty(name = "avatarPath")
    private String avatarPath;
}