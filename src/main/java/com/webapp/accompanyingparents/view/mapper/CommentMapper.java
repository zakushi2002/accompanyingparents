package com.webapp.accompanyingparents.view.mapper;

import com.webapp.accompanyingparents.model.Comment;
import com.webapp.accompanyingparents.view.dto.comment.CommentDto;
import com.webapp.accompanyingparents.view.form.comment.CreateCommentForm;
import com.webapp.accompanyingparents.view.form.comment.UpdateCommentForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PostMapper.class, AccountMapper.class})
public interface CommentMapper {
    @Mapping(source = "contentComment", target = "content")
    @Mapping(source = "postId", target = "post.id")
    @BeanMapping(ignoreByDefault = true)
    Comment formCreateComment(CreateCommentForm createCommentForm);

    @Mapping(source = "contentComment", target = "content")
    @Mapping(source = "id", target = "id")
    @BeanMapping(ignoreByDefault = true)
    void mappingForUpdateComment(UpdateCommentForm updateCommentForm, @MappingTarget Comment comment);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "post", target = "postComment", qualifiedByName ="fromEntityToPostDtoShort")
    @Mapping(source = "content", target = "contentComment")
    @Mapping(source = "account", target = "accountComment")
    @Mapping(source = "account.role", target = "accountComment.roleDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "hasChild", target = "hasChild")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCommentDto")
    CommentDto fromEntityToCommentDto(Comment comment);

    @IterableMapping(elementTargetType = CommentDto.class, qualifiedByName = "fromEntityToCommentDto")
    List<CommentDto> fromEntitiesToCommentDtoList(List<Comment> comments);
}