package com.sadbeast.web.handlers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.ResponseCodeHandler;
import io.undertow.util.Headers;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.StringWriter;

@Singleton
public class PageHandler implements HttpHandler{
    private final Configuration config;

    @Inject
    public PageHandler(final Configuration config) {
        this.config = config;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        String templateName = exchange.getResolvedPath().substring(1) + ".ftl";
        Template template;
        try {
            template = config.getTemplate(templateName);
        } catch (IOException e) {
            ResponseCodeHandler.HANDLE_404.handleRequest(exchange);
            return;
        }

        StringWriter stringWriter = new StringWriter();
        template.process(null, stringWriter);

        exchange.getResponseSender().send(stringWriter.toString());
    }
}
