package com.sadbeast.dto;

import java.util.Date;

public class PostDto {
    private String content;
    private Date created;
    private Long index;

    public PostDto() {
    }

    public PostDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }
}
