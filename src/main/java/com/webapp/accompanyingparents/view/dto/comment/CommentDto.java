package com.webapp.accompanyingparents.view.dto.comment;

import com.webapp.accompanyingparents.view.dto.ABasicAdminDto;
import com.webapp.accompanyingparents.view.dto.account.AccountDto;
import com.webapp.accompanyingparents.view.dto.post.PostDto;
import lombok.Data;

@Data
public class CommentDto extends ABasicAdminDto {
    private PostDto postComment;
    private String contentComment;
    private AccountDto accountComment;
    private CommentDto commentDto;
    private Boolean hasChild;
}