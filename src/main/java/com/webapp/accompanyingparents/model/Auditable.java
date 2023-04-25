package com.webapp.accompanyingparents.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public abstract class Auditable<T> {
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private T createdBy;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @LastModifiedBy
    @Column(name = "modified_by", nullable = false)
    private T modifiedBy;

    @LastModifiedDate
    @Column(name = "modified_date", nullable = false)
    private Date modifiedDate;

    private Integer status = 1;
}