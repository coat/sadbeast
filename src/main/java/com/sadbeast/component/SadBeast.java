package com.sadbeast.component;

import com.sadbeast.module.SadBeastModule;
import com.sadbeast.web.handlers.ApiHandler;
import com.sadbeast.web.handlers.PostHandler;
import com.sadbeast.web.handlers.admin.DashboardHandler;
import com.sadbeast.web.handlers.admin.LoginHandler;
import com.sadbeast.web.security.SBAuthenticationMechanism;
import com.sadbeast.web.security.SBIdentityManager;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = SadBeastModule.class)
public interface SadBeast {
    PostHandler postHandler();

    LoginHandler loginHandler();

    DashboardHandler dashboardHandler();

    ApiHandler apiHandler();

    SBIdentityManager identityManager();

    SBAuthenticationMechanism authenticationMechanism();
}
