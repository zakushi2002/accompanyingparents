package com.webapp.accompanyingparents.view.dto.comment;

import com.webapp.accompanyingparents.model.Comment;
import com.webapp.accompanyingparents.view.dto.ABasicAdminDto;
import com.webapp.accompanyingparents.view.dto.account.AccountDto;
import com.webapp.accompanyingparents.view.dto.post.PostDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.webapp.accompanyingparents.config.constant.APConstant.DATE_TIME_FORMAT;

@Data
public class CommentDto extends ABasicAdminDto {
    private final Comment comment;
    private PostDto postComment;
    private String contentComment;
    private AccountDto accountComment;

    public CommentDto(Comment comment) {
        this.comment = comment;
    }

    @Override
    protected Long getId() {
        return comment.getId();
    }

    @Override
    protected Integer getStatus() {
        return comment.getStatus();
    }

    @Override
    protected LocalDateTime getCreatedDate() {
        return LocalDateTime.parse(comment.getCreatedDate().toString(), DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    @Override
    protected LocalDateTime getModifiedDate() {
        return LocalDateTime.parse(comment.getModifiedDate().toString(), DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
}