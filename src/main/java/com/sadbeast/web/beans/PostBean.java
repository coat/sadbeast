package com.sadbeast.web.beans;

import javax.validation.constraints.Size;

public class PostBean {
    @Size(min = 10, max = 1024, message = "posts.error")
    private String original;

    private String content;
    private String ip;

    public PostBean(String original, String ip) {
        this.original = original;
        this.ip = ip;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}
