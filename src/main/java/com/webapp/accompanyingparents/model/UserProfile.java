package com.webapp.accompanyingparents.model;

import lombok.Getter;
import lombok.Setter;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "account_id")
    @MapsId
    private Account account;
    private Date dob;
    private String phone;
}
