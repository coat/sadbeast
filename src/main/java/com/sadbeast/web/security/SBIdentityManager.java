package com.sadbeast.web.security;

import com.sadbeast.service.UserService;
import io.undertow.security.idm.Account;
import io.undertow.security.idm.Credential;
import io.undertow.security.idm.IdentityManager;
import io.undertow.security.idm.PasswordCredential;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SBIdentityManager implements IdentityManager {
    private final UserService userService;

    @Inject
    public SBIdentityManager(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public Account verify(Account account) {
        return account;
    }

    @Override
    public Account verify(String id, Credential credential) {
        if (credential instanceof PasswordCredential) {
            return userService.authenticate(id, String.valueOf(((PasswordCredential)credential).getPassword()));
        }
        return null;
    }

    @Override
    public Account verify(Credential credential) {
        return null;
    }
}
