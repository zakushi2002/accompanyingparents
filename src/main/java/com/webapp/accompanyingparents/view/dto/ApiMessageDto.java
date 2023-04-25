package com.webapp.accompanyingparents.view.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiMessageDto<T> {
    private Boolean result = true;
    private String code = null;
    private T data = null;
    private String message = null;
}