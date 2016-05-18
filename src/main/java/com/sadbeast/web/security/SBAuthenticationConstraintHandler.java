package com.sadbeast.web.security;

import com.typesafe.config.ConfigFactory;
import io.undertow.security.handlers.AuthenticationConstraintHandler;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.AttachmentKey;

import java.util.regex.Pattern;

public class SBAuthenticationConstraintHandler extends AuthenticationConstraintHandler {
    public static final AttachmentKey<Boolean> AUTH_OPTIONAL_ATTACHMENT_KEY = AttachmentKey.create(Boolean.class);

    private static final Pattern WHITELIST = Pattern.compile(ConfigFactory.load().getString("security.whitelist"));
    private static final Pattern AUTH_OPTIONAL = Pattern.compile(ConfigFactory.load().getString("security.auth_optional"));

    public SBAuthenticationConstraintHandler(HttpHandler next) {
        super(next);
    }

    @Override
    protected boolean isAuthenticationRequired(HttpServerExchange exchange) {
        if (WHITELIST.matcher(exchange.getRequestURI()).matches()) {
            return false;
        }

        if (AUTH_OPTIONAL.matcher(exchange.getRequestURI()).matches()) {
            exchange.putAttachment(AUTH_OPTIONAL_ATTACHMENT_KEY, true);
        }

        return true;
    }
}
