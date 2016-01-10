package com.sadbeast.web.handlers;

import com.sadbeast.SadBeastApplication;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.Methods;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class WebHandler implements HttpHandler {
    private static final Configuration config = new Configuration(Configuration.VERSION_2_3_21);

    static {
        config.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        config.setClassForTemplateLoading(SadBeastApplication.class, "templates");
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

            Map<String, Object> model = new HashMap<>();
            model.put("msg", ResourceBundle.getBundle("messages"));

            String templateName = get(exchange, model);
            Template template = config.getTemplate(templateName + ".ftl");

            StringWriter stringWriter = new StringWriter();
            template.process(model, stringWriter);

            exchange.getResponseSender().send(stringWriter.toString());
        } else if (exchange.getRequestMethod().equals(Methods.POST)) {
            post(exchange);
        }
    }
}
