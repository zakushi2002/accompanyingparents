package com.webapp.accompanyingparents.service;

import com.webapp.accompanyingparents.model.Account;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, targetDomainObject.toString().toUpperCase(), permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        //System.out.println(auth.getName() + auth.getAuthorities().toString() + auth.getDetails().toString());
        return hasPrivilege(auth, targetType.toUpperCase(), permission.toString().toUpperCase());
    }

    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        Account user = (Account) auth.getPrincipal();
        String pCode = user.getRole().getPermissions().get(0).getPCode().toString();
        String[] aList = pCode.split(", ");
        for (int i = 0; i < aList.length; i++) {
            if (aList[i].startsWith(targetType) && aList[i].endsWith(permission) /*grantedPermission.getPCode().contains(permission)*/) {
                System.out.println(aList[i] + " Successfully");
                return true;
            }
        }
        return false;
    }
}
