package com.sadbeast.component;

import com.sadbeast.module.SadBeastModule;
import com.sadbeast.web.handlers.ApiHandler;
import com.sadbeast.web.handlers.IndexHandler;
import com.sadbeast.web.handlers.PostHandler;
import com.sadbeast.web.handlers.admin.DashboardHandler;
import com.sadbeast.web.handlers.admin.LoginHandler;
import com.sadbeast.web.handlers.topic.NewTopicHandler;
import com.sadbeast.web.handlers.topic.TopicHandler;
import com.sadbeast.web.security.SBAuthenticationMechanism;
import com.sadbeast.web.security.SBIdentityManager;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = SadBeastModule.class)
public interface SadBeast {
    PostHandler postHandler();

    DashboardHandler dashboardHandler();

    ApiHandler apiHandler();

    IndexHandler indexHandler();

    TopicHandler topicHandler();
    NewTopicHandler newTopicHandler();

    SBIdentityManager identityManager();

    SBAuthenticationMechanism authenticationMechanism();
}
