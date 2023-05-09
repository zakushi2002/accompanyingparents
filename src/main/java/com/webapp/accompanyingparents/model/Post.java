package com.webapp.accompanyingparents.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "post")
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Post extends Auditable<String> implements IPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", columnDefinition = "LONGTEXT")
    private String title;
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "account_id")
    private Account account;
    private Integer type;

    @Override
    public Integer getClassify() {
        return 1;
    }
}