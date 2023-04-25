package com.webapp.accompanyingparents.model.repository;

import com.webapp.accompanyingparents.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    public Account findAccountByEmail(String email);

    public Account findAccountByResetPwdCode(String resetPwdCode);

    Optional<Account> findByEmail(String email);
}