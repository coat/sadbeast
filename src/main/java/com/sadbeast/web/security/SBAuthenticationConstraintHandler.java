package com.sadbeast.web.security;

import com.typesafe.config.ConfigFactory;
import io.undertow.security.handlers.AuthenticationConstraintHandler;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Methods;

import java.util.List;

public class SBAuthenticationConstraintHandler extends AuthenticationConstraintHandler {
    public SBAuthenticationConstraintHandler(HttpHandler next) {
        super(next);
    }

    @Override
    protected boolean isAuthenticationRequired(HttpServerExchange exchange) {
        List<String> whitelist = ConfigFactory.load().getStringList("security.whitelist");

        return !whitelist.stream().anyMatch(url -> exchange.getRequestURI().matches(url));
    }
}
