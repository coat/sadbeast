package com.sadbeast.web.handlers;

import com.sadbeast.service.TopicService;
import io.undertow.server.HttpServerExchange;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class IndexHandler extends WebHandler {
    private final TopicService topicService;

    @Inject
    public IndexHandler(final TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    protected String get(HttpServerExchange exchange, Map<String, Object> model) {
        model.put("topics", topicService.getTopicSummaries());

        return "index";
    }
}
