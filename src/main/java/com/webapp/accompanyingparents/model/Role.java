package com.webapp.accompanyingparents.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "role")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class Role extends Auditable<String> implements Serializable {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "com.webapp.accompanyingparents.model.utils.SequenceGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "description", length = 1000)
    private String description;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = TablePrefix.PREFIX_TABLE + "permission_role",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id",
                    referencedColumnName = "id"))
    private List<Permission> permissions = new ArrayList<>();

    @Override
    public String toString() {
        return "[" + name + "]";
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Permission permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission.getPCode()));
        }
        return authorities;
    }

    public void addPermission(Permission permission) {
        this.permissions.add(permission);
    }

    public Role(String name) {
        this.name = name;
    }
}