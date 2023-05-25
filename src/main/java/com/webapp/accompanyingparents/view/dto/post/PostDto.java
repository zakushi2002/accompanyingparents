package com.webapp.accompanyingparents.view.dto.post;

import com.webapp.accompanyingparents.view.dto.ABasicAdminDto;
import com.webapp.accompanyingparents.view.dto.account.AccountDto;
import lombok.Data;

@Data
public class PostDto extends ABasicAdminDto {
    private String titlePost;
    private String contentPost;
    private AccountDto accountPost;
    private Integer typePost;
}