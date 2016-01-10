package com.sadbeast.web.handlers.admin;

import com.sadbeast.service.PostService;
import com.sadbeast.web.handlers.WebHandler;
import io.undertow.server.HttpServerExchange;

import javax.inject.Inject;
import java.util.Map;

public class DashboardHandler extends WebHandler {
    private final PostService postService;

    @Inject
    public DashboardHandler(final PostService postService) {
        this.postService = postService;
    }

    @Override
    protected String get(HttpServerExchange exchange, Map<String, Object> model) {
        model.put("count", postService.count());

        return "admin/dashboard";
    }
}
