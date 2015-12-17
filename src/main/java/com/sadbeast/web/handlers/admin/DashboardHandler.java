package com.sadbeast.web.handlers.admin;

import com.sadbeast.service.PostService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import javax.inject.Inject;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class DashboardHandler implements HttpHandler {
    private final Configuration config;
    private final PostService postService;

    @Inject
    public DashboardHandler(final Configuration config, final PostService postService) {
        this.config = config;
        this.postService = postService;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("count", postService.count());

        Template template = config.getTemplate("admin/dashboard.ftl");
        StringWriter stringWriter = new StringWriter();
        template.process(model, stringWriter);

        exchange.getResponseSender().send(stringWriter.toString());
    }
}
