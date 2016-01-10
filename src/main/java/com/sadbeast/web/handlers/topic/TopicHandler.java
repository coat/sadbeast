package com.sadbeast.web.handlers.topic;

import com.sadbeast.service.TopicService;
import com.sadbeast.web.handlers.WebHandler;
import io.undertow.server.HttpServerExchange;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class TopicHandler extends WebHandler {
    private final TopicService topicService;

    @Inject
    public TopicHandler(final TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    protected String get(HttpServerExchange exchange, Map<String, Object> model) {
        model.put("topic", topicService.getTopic(Long.valueOf(exchange.getQueryParameters().get("id").getFirst())));

        return "topic";
    }
}
