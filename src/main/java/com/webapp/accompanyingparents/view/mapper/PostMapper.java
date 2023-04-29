package com.webapp.accompanyingparents.view.mapper;

import com.webapp.accompanyingparents.model.Post;
import com.webapp.accompanyingparents.view.dto.post.PostDto;
import com.webapp.accompanyingparents.view.form.post.CreatePostForm;
import com.webapp.accompanyingparents.view.form.post.UpdatePostForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AccountMapper.class, RoleMapper.class})
public interface PostMapper {
    @Mapping(source = "titlePost", target = "title")
    @Mapping(source = "contentPost", target = "content")
    @BeanMapping(ignoreByDefault = true)
    Post formCreatePost(CreatePostForm createPostForm);

    @Mapping(source = "titlePost", target = "title")
    @Mapping(source = "contentPost", target = "content")
    @BeanMapping(ignoreByDefault = true)
    void mappingForUpdatePost(UpdatePostForm updatePostForm, @MappingTarget Post post);

    @Mapping(source = "id", target = "post.id")
    @Mapping(source = "title", target = "titlePost")
    @Mapping(source = "content", target = "contentPost")
    @Mapping(source = "account", target = "accountPost")
    @Mapping(source = "account.role", target = "accountPost.roleDto")
    @Mapping(source = "createdDate", target = "post.createdDate")
    @Mapping(source = "createdDate", target = "post.modifiedDate")
    @Named("fromEntityToPostDto")
    @BeanMapping(ignoreByDefault = true)
    PostDto fromEntityToPostDto(Post post);

    @IterableMapping(elementTargetType = PostDto.class, qualifiedByName = "fromEntityToPostDto")
    List<PostDto> fromEntitiesToPostDtoList(List<Post> posts);
    @Mapping(source = "id", target = "post.id")
    @Mapping(source = "title", target = "titlePost")
    @Mapping(source = "content", target = "contentPost")
    @Named("fromEntityToPostDtoShort")
    @BeanMapping(ignoreByDefault = true)
    PostDto fromEntityToPostDtoShort(Post post);

    @IterableMapping(elementTargetType = PostDto.class, qualifiedByName = "fromEntityToPostDtoShort")
    List<PostDto> fromEntitiesToPostDtoListShort(List<Post> posts);
}