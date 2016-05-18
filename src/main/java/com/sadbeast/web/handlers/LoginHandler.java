package com.sadbeast.web.handlers;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.Session;
import io.undertow.util.Sessions;

import java.util.*;

public class LoginHandler extends WebHandler {
    @Override
    protected String get(HttpServerExchange exchange, Map<String, Object> model) {
        Session session = Sessions.getOrCreateSession(exchange);

        String username = "";
        if (session.getAttribute("username") != null) {
            username = (String) session.removeAttribute("username");
        }

        model.put("username", username);

        return "login";
    }
}
