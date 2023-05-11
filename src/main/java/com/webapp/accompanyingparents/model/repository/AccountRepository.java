package com.webapp.accompanyingparents.model.repository;

import com.webapp.accompanyingparents.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Account findAccountByEmail(String email);

    Account findAccountByResetPwdCode(String resetPwdCode);

    Optional<Account> findByEmail(String email);
}