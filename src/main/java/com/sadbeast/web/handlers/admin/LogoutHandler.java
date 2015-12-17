package com.sadbeast.web.handlers.admin;

import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.CookieImpl;

import java.util.Date;

public class LogoutHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.getResponseCookies().put("auth", new CookieImpl("auth").setExpires(new Date(1)));

        exchange.dispatch(Handlers.redirect("/admin/login"));
    }
}
