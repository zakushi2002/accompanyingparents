package com.webapp.accompanyingparents.view.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public abstract class ABasicAdminDto {
    @ApiModelProperty(name = "id")
    private Long id;

    @ApiModelProperty(name = "status")
    private Integer status;

    @ApiModelProperty(name = "modifiedDate")
    private Date modifiedDate;

    @ApiModelProperty(name = "createdDate")
    private Date createdDate;
}
