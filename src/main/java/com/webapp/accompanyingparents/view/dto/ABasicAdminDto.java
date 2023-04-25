package com.webapp.accompanyingparents.view.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public abstract class ABasicAdminDto {
    public void render() {
        Long id = getId();
        Integer status = getStatus();
        LocalDateTime createdDate = getCreatedDate();
        LocalDateTime modifiedDate = getModifiedDate();
    }

    protected abstract Long getId();

    protected abstract Integer getStatus();

    protected abstract LocalDateTime getCreatedDate();

    protected abstract LocalDateTime getModifiedDate();
}
