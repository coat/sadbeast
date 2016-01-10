package com.sadbeast.web.handlers.admin;

import com.sadbeast.web.handlers.WebHandler;
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
        if (session.getAttribute("errors") != null) {
            model.put("errors", session.removeAttribute("errors"));
        }

        model.put("msg", ResourceBundle.getBundle("messages"));

        return "admin/login";
    }
}
