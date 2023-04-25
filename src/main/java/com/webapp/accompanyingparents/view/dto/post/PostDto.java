package com.webapp.accompanyingparents.view.dto.post;

import com.webapp.accompanyingparents.model.Post;
import com.webapp.accompanyingparents.view.dto.ABasicAdminDto;
import com.webapp.accompanyingparents.view.dto.account.AccountDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.webapp.accompanyingparents.config.constant.APConstant.DATE_TIME_FORMAT;

@Data
public class PostDto extends ABasicAdminDto {
    private final Post post;
    private String titlePost;
    private String contentPost;
    private AccountDto accountPost;

    public PostDto(Post post) {
        this.post = post;
    }

    @Override
    protected Long getId() {
        return post.getId();
    }

    @Override
    protected Integer getStatus() {
        return post.getStatus();
    }

    @Override
    protected LocalDateTime getCreatedDate() {
        return LocalDateTime.parse(post.getCreatedDate().toString(), DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    @Override
    protected LocalDateTime getModifiedDate() {
        return LocalDateTime.parse(post.getModifiedDate().toString(), DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
}