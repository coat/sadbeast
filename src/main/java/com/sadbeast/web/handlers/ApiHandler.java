package com.sadbeast.web.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sadbeast.dto.PostDto;
import com.sadbeast.service.PostService;
import com.sadbeast.util.RemoteIP;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.ResponseCodeHandler;
import io.undertow.util.Headers;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class ApiHandler implements HttpHandler {
    private final ObjectMapper mapper;
    private final PostService postService;
    private final Configuration config;

    @Inject
    public ApiHandler(final ObjectMapper mapper, final PostService postService, final Configuration config) {
        this.mapper = mapper;
        this.postService = postService;
        this.config = config;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        // Redirect to swagger
        if (exchange.getRelativePath().equals("/api") || exchange.getRelativePath().isEmpty()) {
            String scheme = exchange.getRequestScheme();
            if (RemoteIP.inProxy(exchange)) {
                scheme = "https";
            }
            String baseUrl = scheme + "://" + exchange.getHostAndPort();
            String apiDocsUrl = baseUrl + "/api-docs/?url=" + baseUrl + "/api/swagger.json";

            exchange.dispatch(Handlers.redirect(apiDocsUrl));
            return;
        }

        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");

        if (exchange.getRelativePath().startsWith("/api/posts")) {
            List<PostDto> posts;
            if (exchange.getQueryParameters().containsKey("prev")) {
                posts = postService.findLatestPosts(Long.valueOf(exchange.getQueryParameters().get("prev").getFirst()));
            } else if (exchange.getQueryParameters().containsKey("q")) {
                posts = postService.search(exchange.getQueryParameters().get("q").getFirst());
            } else {
                posts = postService.findLatestPosts();
            }
            exchange.getResponseSender().send(mapper.writeValueAsString(posts));
            return;
        }
        
        if (exchange.getRelativePath().startsWith("/api/post") && exchange.getQueryParameters().containsKey("random")) {
            exchange.getResponseSender().send(mapper.writeValueAsString(postService.random()));
            return;
        }

        if (exchange.getRelativePath().equals("/api/swagger.json")) {
            Map<String, Object> model = new HashMap<>();

            String scheme = exchange.getRequestScheme();
            if (RemoteIP.inProxy(exchange)) {
                scheme = "https";
            }
            model.put("scheme", scheme);
            model.put("host", exchange.getHostAndPort());

            Template template = config.getTemplate("api/swagger.ftl");
            StringWriter stringWriter = new StringWriter();
            template.process(model, stringWriter);

            exchange.getResponseSender().send(stringWriter.toString());
            return;
        }

        ResponseCodeHandler.HANDLE_404.handleRequest(exchange);
    }
}
