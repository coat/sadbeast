package com.sadbeast.web.handlers.topic;

import com.sadbeast.service.TopicService;
import com.sadbeast.web.beans.TopicBean;
import com.sadbeast.web.handlers.WebHandler;
import io.undertow.Handlers;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.server.session.Session;
import io.undertow.util.Sessions;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Singleton
public class NewTopicHandler extends WebHandler {
    private final Validator validator;
    private final TopicService topicService;

    @Inject
    public NewTopicHandler(final Validator validator, final TopicService topicService) {
        this.validator = validator;
        this.topicService = topicService;
    }

    @Override
    protected String get(HttpServerExchange exchange, Map<String, Object> model) {
        Session session = Sessions.getOrCreateSession(exchange);
        TopicBean topic = new TopicBean();
        if (session.getAttribute("topic") != null) {
            topic = (TopicBean) session.removeAttribute("topic");
        }

        model.put("topic", topic);
        if (session.getAttribute("errors") != null) {
            model.put("errors", session.removeAttribute("errors"));
        }

        return "new_topic";
    }

    @Override
    protected void post(HttpServerExchange exchange) {
        Session session = Sessions.getOrCreateSession(exchange);

        FormDataParser parser = FormParserFactory.builder().build().createParser(exchange);
        FormData formData;
        try {
            formData = parser.parseBlocking();
            TopicBean topic = new TopicBean(formData);
            topic.setUserId(2L);

            Set<ConstraintViolation<TopicBean>> violations = validator.validate(topic);
            if (violations.isEmpty()) {
                topicService.create(topic);
                session.removeAttribute("topic");
                exchange.dispatch(Handlers.redirect("/"));
            } else {
                session.setAttribute("errors", violations);
                session.setAttribute("topic", topic);
                exchange.dispatch(Handlers.redirect("/topic"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
