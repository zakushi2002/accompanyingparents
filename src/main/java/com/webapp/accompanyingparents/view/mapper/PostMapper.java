package com.webapp.accompanyingparents.view.mapper;

import com.webapp.accompanyingparents.model.Post;
import com.webapp.accompanyingparents.view.dto.post.PostDto;
import com.webapp.accompanyingparents.view.form.post.CreatePostForm;
import com.webapp.accompanyingparents.view.form.post.UpdatePostForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class})
public interface PostMapper {
    @Mapping(source = "titlePost", target = "title")
    @Mapping(source = "contentPost", target = "content")
    @BeanMapping(ignoreByDefault = true)
    Post formCreatePost(CreatePostForm createPostForm);

    @Mapping(source = "titlePost", target = "title")
    @Mapping(source = "contentPost", target = "content")
    @BeanMapping(ignoreByDefault = true)
    void mappingForUpdatePost(UpdatePostForm updatePostForm, @MappingTarget Post post);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "titlePost")
    @Mapping(source = "content", target = "contentPost")
    @Mapping(source = "type", target = "typePost")
    @Mapping(source = "account", target = "accountPost", qualifiedByName = "fromAccountForPostToDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Named("fromEntityToPostDto")
    @BeanMapping(ignoreByDefault = true)
    PostDto fromEntityToPostDto(Post post);

    @IterableMapping(elementTargetType = PostDto.class, qualifiedByName = "fromEntityToPostDto")
    List<PostDto> fromEntitiesToPostDtoList(List<Post> posts);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "titlePost")
    @Mapping(source = "content", target = "contentPost")
    @Named("fromEntityToPostDtoShort")
    @BeanMapping(ignoreByDefault = true)
    PostDto fromEntityToPostDtoShort(Post post);

    @IterableMapping(elementTargetType = PostDto.class, qualifiedByName = "fromEntityToPostDtoShort")
    List<PostDto> fromEntitiesToPostDtoListShort(List<Post> posts);
}