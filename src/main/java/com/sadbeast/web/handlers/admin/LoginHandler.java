package com.sadbeast.web.handlers.admin;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.Session;
import io.undertow.util.Headers;
import io.undertow.util.Sessions;

import javax.inject.Inject;
import java.io.StringWriter;
import java.util.*;

public class LoginHandler implements HttpHandler {
    private final Configuration config;

    @Inject
    public LoginHandler(final Configuration config) {
        this.config = config;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Session session = Sessions.getOrCreateSession(exchange);

        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        Map<String, Object> model = new HashMap<>();
        String username = "";
        if (session.getAttribute("username") != null) {
            username = (String) session.removeAttribute("username");
        }

        model.put("username", username);
        if (session.getAttribute("errors") != null) {
            model.put("errors", session.removeAttribute("errors"));
        }

        model.put("msg", ResourceBundle.getBundle("messages"));
        Template template = config.getTemplate("admin/login.ftl");
        StringWriter stringWriter = new StringWriter();
        template.process(model, stringWriter);

        exchange.getResponseSender().send(stringWriter.toString());
    }
}
