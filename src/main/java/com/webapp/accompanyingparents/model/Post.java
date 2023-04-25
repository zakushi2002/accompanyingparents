package com.webapp.accompanyingparents.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "post")
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Post extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.webapp.accompanyingparents.model.utils.SequenceGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @Column(name = "title", columnDefinition = "LONGTEXT")
    private String title;
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "account_id")
    private Account account;
}