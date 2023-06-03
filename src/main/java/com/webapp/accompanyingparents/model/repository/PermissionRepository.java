package com.webapp.accompanyingparents.model.repository;

import com.webapp.accompanyingparents.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findFirstByName(String name);
}