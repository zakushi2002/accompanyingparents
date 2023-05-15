package com.webapp.accompanyingparents.view.mapper;

import com.webapp.accompanyingparents.model.Bookmark;
import com.webapp.accompanyingparents.view.dto.bookmark.BookmarkDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PostMapper.class})
public interface BookmarkMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "post", target = "postDto", qualifiedByName = {"fromEntityToPostDto"})
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Named("fromEntityToBookmarkDto")
    @BeanMapping(ignoreByDefault = true)
    BookmarkDto fromEntityToBookmarkDto(Bookmark bookmark);

    @IterableMapping(elementTargetType = BookmarkDto.class, qualifiedByName = "fromEntityToBookmarkDto")
    List<BookmarkDto> fromEntitiesToBookmarkDtoList(List<Bookmark> bookmarks);
}