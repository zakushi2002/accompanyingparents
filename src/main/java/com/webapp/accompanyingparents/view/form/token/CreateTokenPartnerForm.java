package com.webapp.accompanyingparents.view.form.token;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateTokenPartnerForm {
    @NotNull(message = "name can not be null")
    @ApiModelProperty(name = "name", required = false)
    private String name;
}