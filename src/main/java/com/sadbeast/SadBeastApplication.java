package com.sadbeast;

import com.sadbeast.component.DaggerSadBeast;
import com.sadbeast.component.SadBeast;
import com.sadbeast.web.security.SBAuthenticationConstraintHandler;
import com.sadbeast.web.handlers.admin.LogoutHandler;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.security.api.AuthenticationMechanism;
import io.undertow.security.api.AuthenticationMode;
import io.undertow.security.handlers.AuthenticationCallHandler;
import io.undertow.security.handlers.AuthenticationMechanismsHandler;
import io.undertow.security.handlers.SecurityInitialHandler;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.accesslog.AccessLogHandler;
import io.undertow.server.handlers.error.SimpleErrorPageHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.session.InMemorySessionManager;
import io.undertow.server.session.SessionAttachmentHandler;
import io.undertow.server.session.SessionCookieConfig;
import io.undertow.server.session.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class SadBeastApplication implements Runnable {
    private static SadBeast sadBeast;

    @Override
    public void run() {
        Config config = ConfigFactory.load();

        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
        System.setProperty("logs.location", config.getString("logs.location"));

        Logger log = LogManager.getLogger();

        SessionManager sessionManager = new InMemorySessionManager("SESSION_MANAGER");
        SessionCookieConfig sessionConfig = new SessionCookieConfig();
        sessionConfig.setCookieName("session");

        Undertow server = Undertow.builder()
                .addHttpListener(config.getInt("web.port"), "localhost")
                .setHandler(
                        new AccessLogHandler(
                                new SimpleErrorPageHandler(
                                        new SessionAttachmentHandler(
                                                addSecurity(
                                                        Handlers.path(
                                                                Handlers.resource(new ClassPathResourceManager(SadBeastApplication.class.getClassLoader(), "public"))
                                                        )
                                                                .addExactPath("/", sadBeast.postHandler())
                                                                .addExactPath("/admin/login", sadBeast.loginHandler())
                                                                .addExactPath("/admin/logout", new LogoutHandler())
                                                                .addExactPath("/admin", sadBeast.dashboardHandler())
                                                                .addPrefixPath("/api", sadBeast.apiHandler())

                                                ), sessionManager, sessionConfig)
                                ), log::info, "common", SadBeastApplication.class.getClassLoader()
                        )
                ).build();

        server.start();
    }

    private HttpHandler addSecurity(final HttpHandler toWrap) {
        HttpHandler handler = toWrap;
        handler = new AuthenticationCallHandler(handler);
        handler = new SBAuthenticationConstraintHandler(handler);
        final List<AuthenticationMechanism> mechanisms = Collections.<AuthenticationMechanism>singletonList(sadBeast.authenticationMechanism());
        handler = new AuthenticationMechanismsHandler(handler, mechanisms);
        handler = new SecurityInitialHandler(AuthenticationMode.CONSTRAINT_DRIVEN, sadBeast.identityManager(), handler);

        return handler;
    }

    public static void main(String[] args) {
        sadBeast = DaggerSadBeast.create();

        new SadBeastApplication().run();
    }
}