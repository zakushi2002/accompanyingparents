package com.webapp.accompanyingparents.view.dto.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ForgetPasswordDto {
    @ApiModelProperty(name = "idHash")
    private String idHash;
}