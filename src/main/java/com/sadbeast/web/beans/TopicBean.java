package com.sadbeast.web.beans;

import io.undertow.server.handlers.form.FormData;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TopicBean {
    @Size(min = 1)
    private String title = "";
    @Size(min = 1)
    private String content = "";
    @NotNull
    private Long userId;

    public TopicBean(final FormData form) {
        this.title = form.getFirst("title").getValue();
        this.content = form.getFirst("content").getValue();
    }

    public TopicBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
