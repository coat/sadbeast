package com.sadbeast.web.handlers;

import com.sadbeast.dto.TopicDto;
import com.sadbeast.service.TopicService;
import com.sadbeast.web.beans.PostBean;
import com.sadbeast.service.PostService;
import io.undertow.Handlers;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.session.Session;
import io.undertow.util.Sessions;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.*;

@Singleton
public class PostHandler extends WebHandler {
    private final TopicService topicService;
    private final PostService postService;
    private final Validator validator;

    @Inject
    public PostHandler(final TopicService topicService, final PostService postService, final Validator validator) {
        this.topicService = topicService;
        this.postService = postService;
        this.validator = validator;
    }

    @Override
    protected void post(HttpServerExchange exchange) {
        Session session = Sessions.getOrCreateSession(exchange);

        FormDataParser parser = FORM_PARSER_FACTORY.createParser(exchange);
        FormData formData;
        try {
            formData = parser.parseBlocking();
            String postContent = formData.getFirst("content").getValue();

            PostBean post = new PostBean(postContent);
            post.setTopicId(Long.valueOf(param(exchange, "id")));
            post.setUserId(2L);

            TopicDto topic = topicService.getTopicSummary(post.getTopicId());
            Set<ConstraintViolation<PostBean>> violations = validator.validate(post);
            if (violations.isEmpty()) {
                postService.createPost(post);
                session.removeAttribute("post");
                exchange.dispatch(Handlers.redirect(topic.getUrl()));
            } else {
                session.setAttribute("errors", violations);
                session.setAttribute("post", postContent);
                exchange.dispatch(Handlers.redirect(topic.getUrl() + "/post"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String get(HttpServerExchange exchange, Map<String, Object> model) {
        Session session = Sessions.getOrCreateSession(exchange);

        model.put("topic", topicService.getTopic(Long.valueOf(param(exchange, "id"))));

        String post = "";
        if (session.getAttribute("post") != null) {
            post = (String) session.removeAttribute("post");
        }

        model.put("post", post);

        return "new_post";
    }
}
