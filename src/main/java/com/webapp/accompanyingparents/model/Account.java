package com.webapp.accompanyingparents.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "account")
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Component
public class Account extends Auditable<String> implements UserDetails, Serializable {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.webapp.accompanyingparents.model.utils.SequenceGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    private String email;
    private String fullName;
    @JsonIgnore
    private String password;
    private Date lastLogin;
    private String avatarPath;
    private String resetPwdCode;
    private Date resetPwdTime;
    private Boolean isSuperAdmin = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public Account(String email, String fullName, String password, String avatarPath, Boolean isSuperAdmin) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.avatarPath = avatarPath;
        this.isSuperAdmin = isSuperAdmin;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }

    public void addRole(Role role) {
        this.setRole(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}