package br.ufrn.ePET.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_tutor, ROLE_petiano, ROLE_comum;

    public String getAuthority() {
        return name();
    }

}
