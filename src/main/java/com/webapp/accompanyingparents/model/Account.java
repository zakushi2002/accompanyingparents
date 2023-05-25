package com.webapp.accompanyingparents.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webapp.accompanyingparents.config.constant.APConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "account")
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Account extends Auditable<String> implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String fullName;
    @JsonIgnore
    private String password;
    private Date lastLogin;
    @Column(columnDefinition = "LONGTEXT")
    private String avatarPath;
    private String resetPwdCode;
    private Date resetPwdTime;
    private Boolean isSuperAdmin = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
    private Integer attemptOTP;

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

    public boolean isOTPRequired() {
        if (this.resetPwdCode == null) {
            return false;
        }
        long otpRequestedTime = this.resetPwdTime.getTime();
        return otpRequestedTime + APConstant.OTP_VALID_DURATION >= System.currentTimeMillis(); // OTP expires
    }
}