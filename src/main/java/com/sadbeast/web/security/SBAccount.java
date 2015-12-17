package com.sadbeast.web.security;

import io.undertow.security.idm.Account;

import java.security.Principal;
import java.util.Set;

public class SBAccount implements Account {
    private Principal principal;
    private String token;

    public SBAccount(final String username, final String token) {
        this.principal = () -> username;
        this.token = token;
    }

    @Override
    public Principal getPrincipal() {
        return principal;
    }

    @Override
    public Set<String> getRoles() {
        return null;
    }

    public String getToken() {
        return token;
    }
}
