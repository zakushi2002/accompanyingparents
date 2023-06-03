package com.webapp.accompanyingparents.service.factorymethod;

import com.webapp.accompanyingparents.config.constant.APConstant;
import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.Role;
import com.webapp.accompanyingparents.model.repository.RoleRepository;

public class User implements IAccount {
    RoleRepository roleRepository;

    public User(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Account create() {
        Role role = roleRepository.findFirstByName(APConstant.ROLE_END_USER.trim());
        Account account = new Account();
        account.setRole(role);
        return account;
    }
}