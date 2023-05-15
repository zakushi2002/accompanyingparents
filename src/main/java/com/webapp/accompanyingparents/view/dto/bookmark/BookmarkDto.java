package com.webapp.accompanyingparents.view.dto.bookmark;

import com.webapp.accompanyingparents.view.dto.ABasicAdminDto;
import com.webapp.accompanyingparents.view.dto.post.PostDto;
import lombok.Data;

@Data
public class BookmarkDto extends ABasicAdminDto {
    private PostDto postDto;
}