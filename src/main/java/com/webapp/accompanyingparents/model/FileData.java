package com.webapp.accompanyingparents.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "file_data")
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class FileData extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String downloadUri;
    private String type;
    private Long size;

    public FileData(String name, String downloadUri, String type, long size) {
        this.name = name;
        this.downloadUri = downloadUri;
        this.type = type;
        this.size = size;
    }
}