package com.sadbeast.dto;

import java.util.Date;
import java.util.List;

import static humanize.Humanize.naturalTime;

public class TopicDto {
    private Long id;
    private String title;
    private String handle;
    private Integer postCount;
    private Date modified;
    private String author;
    private String lastUser;
    private transient List<PostDto> posts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser;
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }

    public String getUrl() {
        return "/topics/" + id + "/" + handle;
    }

    public String getLastDate() {
        return naturalTime(modified);
    }
}
