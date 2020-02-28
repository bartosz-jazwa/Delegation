package com.jazwa.delegation.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public enum Role{
    ROLE_ADMIN,
    ROLE_EMPLOYEE,
    ROLE_HEAD;
}