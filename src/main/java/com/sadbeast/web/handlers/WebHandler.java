package com.sadbeast.web.handlers;

import com.sadbeast.SadBeastApplication;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.server.session.Session;
import io.undertow.util.Headers;
import io.undertow.util.Methods;
import io.undertow.util.Sessions;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class WebHandler implements HttpHandler {
    private static final Configuration CONFIG = new Configuration(Configuration.VERSION_2_3_23);

    protected static final FormParserFactory FORM_PARSER_FACTORY = FormParserFactory.builder().build();

    static {
        CONFIG.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        CONFIG.setClassForTemplateLoading(SadBeastApplication.class, "templates");
    }

    protected String get(HttpServerExchange exchange, Map<String, Object> model) {
        return "";
    }

    protected void post(HttpServerExchange exchange) {
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if (exchange.getRequestMethod().equals(Methods.GET)) {

            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

            Map<String, Object> model = prepareModel(exchange);

            String templateName = get(exchange, model);
            Template template = CONFIG.getTemplate(templateName + ".ftl");

            StringWriter stringWriter = new StringWriter();
            template.process(model, stringWriter);

            exchange.getResponseSender().send(stringWriter.toString());
        } else if (exchange.getRequestMethod().equals(Methods.POST)) {
            post(exchange);
        }
    }

    private Map<String, Object> prepareModel(final HttpServerExchange exchange) {
        Map<String, Object> model = new HashMap<>();

        model.put("msg", ResourceBundle.getBundle("messages"));

        Session session = Sessions.getOrCreateSession(exchange);
        if (session.getAttribute("errors") != null) {
            model.put("errors", session.removeAttribute("errors"));
        }

        if (exchange.getSecurityContext().isAuthenticated()) {
            model.put("account", exchange.getSecurityContext().getAuthenticatedAccount());
        }

        return model;
    }

    public static String param(final HttpServerExchange exchange, final String name) {
        return exchange.getQueryParameters().get(name).getFirst();
    }
}
