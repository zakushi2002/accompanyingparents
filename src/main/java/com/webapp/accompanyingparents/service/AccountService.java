package com.webapp.accompanyingparents.service;
/*
import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.Role;
import com.webapp.accompanyingparents.model.Permission;
import com.webapp.accompanyingparents.exception.NotFoundException;
import com.webapp.accompanyingparents.repository.AccountRepository;
import com.webapp.accompanyingparents.repository.PermissionRepository;
import com.webapp.accompanyingparents.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;
    public Account saveAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }
    public void addRoleToUser(String username, String roleName) {
        Account user = accountRepository.findByUsername(username).get();
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }
    public void addPermissionToRole(String roleName, String permissionName) {
        Role role = roleRepository.findByName(roleName);
        Permission permission = permissionRepository.findByName(permissionName);
        role.getPermissions().add(permission);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find Account
        Account user = accountRepository.findByUsername(username).get();
        if (user == null) {
            log.error("Account not found in the database");
            throw new UsernameNotFoundException("Account not found in the database");
        }
        else {
            log.info("Account found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        // Return User Spring
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username).get();
    }
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }
    public Account update(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            Account updateAcc = accountRepository.findByUsername(account.getUsername()).get();
            updateAcc.setPhone(account.getPhone());
            updateAcc.setDob(account.getDob());
            return updateAcc;
        }
        throw new NotFoundException("Account does not exist in this system");
    }
}
*/
