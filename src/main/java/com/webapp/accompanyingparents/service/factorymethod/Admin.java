package com.webapp.accompanyingparents.service.factorymethod;

import com.webapp.accompanyingparents.config.constant.APConstant;
import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.Role;
import com.webapp.accompanyingparents.model.repository.RoleRepository;

public class Admin implements IAccount {
    RoleRepository roleRepository;

    public Admin(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Account create() {
        Role role = roleRepository.findFirstByName(APConstant.ROLE_ADMIN.trim());
        Account account = new Account();
        account.setRole(role);
        account.setIsSuperAdmin(false);
        return account;
    }
}