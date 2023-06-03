package com.webapp.accompanyingparents.service.factorymethod;

import com.webapp.accompanyingparents.config.constant.APConstant;
import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.Role;
import com.webapp.accompanyingparents.model.repository.RoleRepository;

public class Expert implements IAccount {
    RoleRepository roleRepository;

    public Expert(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Account create() {
        Role role = roleRepository.findFirstByName(APConstant.ROLE_EXPERT.trim());
        Account account = new Account();
        account.setRole(role);
        return account;
    }
}