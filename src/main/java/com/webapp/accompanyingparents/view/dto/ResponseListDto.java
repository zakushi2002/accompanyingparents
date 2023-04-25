package com.webapp.accompanyingparents.view.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ResponseListDto<T> {
    private static final long serialVersionUID = 1L;
    private T content;
    private Integer pageNumber;
    private Long totalElements;
    private Integer totalPages;
}