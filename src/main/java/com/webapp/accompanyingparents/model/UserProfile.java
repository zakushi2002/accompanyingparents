package com.webapp.accompanyingparents.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "user_profile")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class UserProfile extends Auditable<String> {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.webapp.accompanyingparents.model.utils.SequenceGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @OneToOne
    @JoinColumn(name = "account_id")
    @MapsId
    private Account account;
    private Date dob;
    private String phone;
}
