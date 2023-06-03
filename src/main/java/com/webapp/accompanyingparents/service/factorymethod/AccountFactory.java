package com.webapp.accompanyingparents.service.factorymethod;

import com.webapp.accompanyingparents.model.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountFactory {
    static RoleRepository roleRepository;
    private AccountFactory(RoleRepository roleRepository) {
        AccountFactory.roleRepository = roleRepository;
    }
    public static final IAccount newAccount(AccountType type) {
        switch (type) {
            case ADMIN:
                return new Admin(roleRepository);
            case USER:
                return new User(roleRepository);
            case EXPERT:
                return new Expert(roleRepository);
            default:
                throw new IllegalArgumentException("This account type is unsupported");
        }
    }
}