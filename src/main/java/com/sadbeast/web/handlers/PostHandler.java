package com.sadbeast.web.handlers;

import com.sadbeast.dto.PostDto;
import com.sadbeast.web.beans.PostBean;
import com.sadbeast.service.PostService;
import com.sadbeast.util.RemoteIP;
import freemarker.template.Configuration;
import freemarker.template.Template;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.server.session.Session;
import io.undertow.util.Headers;
import io.undertow.util.Methods;
import io.undertow.util.Sessions;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.StringWriter;
import java.util.*;

@Singleton
public class PostHandler implements HttpHandler {
    private final Configuration config;
    private final PostService postService;
    private final Validator validator;

    @Inject
    public PostHandler(final Configuration config, final PostService postService, final Validator validator) {
        this.config = config;
        this.postService = postService;
        this.validator = validator;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Session session = Sessions.getOrCreateSession(exchange);

        if (exchange.getRequestMethod().equals(Methods.POST)) {
            FormDataParser parser = FormParserFactory.builder().build().createParser(exchange);
            FormData formData = parser.parseBlocking();
            String postContent = formData.get("post").getLast().getValue();

            PostBean post = new PostBean(postContent, RemoteIP.getIp(exchange));

            Set<ConstraintViolation<PostBean>> violations = validator.validate(post);
            if (violations.isEmpty()) {
                postService.createPost(post);
            } else {
                session.setAttribute("errors", violations);
                session.setAttribute("post", postContent);
            }
            exchange.dispatch(Handlers.redirect("/"));
            return;
        }
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        Map<String, Object> model = new HashMap<>();
        List<PostDto> posts;
        if (exchange.getQueryParameters().containsKey("prev")) {
            posts = postService.findLatestPosts(Long.valueOf(exchange.getQueryParameters().get("prev").getFirst()));
        } else {
            posts = postService.findLatestPosts();
        }
        model.put("posts", posts);
        String post = "";
        if (session.getAttribute("post") != null) {
            post = (String) session.removeAttribute("post");
        }

        model.put("post", post);
        if (session.getAttribute("errors") != null) {
            model.put("errors", session.removeAttribute("errors"));
        }

        model.put("msg", ResourceBundle.getBundle("messages"));
        Template template = config.getTemplate("index.ftl");
        StringWriter stringWriter = new StringWriter();
        template.process(model, stringWriter);

        exchange.getResponseSender().send(stringWriter.toString());
    }
}
