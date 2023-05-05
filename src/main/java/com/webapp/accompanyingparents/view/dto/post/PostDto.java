package com.webapp.accompanyingparents.view.dto.post;

import com.webapp.accompanyingparents.view.dto.ABasicAdminDto;
import com.webapp.accompanyingparents.view.dto.account.AccountDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PostDto extends ABasicAdminDto {
    @ApiModelProperty(name = "titlePost")
    private String titlePost;
    @ApiModelProperty(name = "contentPost")
    private String contentPost;
    @ApiModelProperty(name = "accountPost")
    private AccountDto accountPost;
}