package com.sadbeast.dao;

import com.sadbeast.dto.PostDto;
import com.sadbeast.web.beans.PostBean;
import org.dalesbred.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class PostDao {
    private static final int PAGE_LIMIT = 20;

    private final Database db;

    @Inject
    public PostDao(final Database db) {
        this.db = db;
    }

    public List<PostDto> findLatestPosts() {
        return db.findAll(PostDto.class, "SELECT post_id AS index, content, created " +
                "FROM posts ORDER BY post_id DESC LIMIT " + PAGE_LIMIT);
    }

    public List<PostDto> seek(final Long prevPostId) {
        return db.findAll(PostDto.class, "SELECT post_id AS index, content, created FROM posts " +
                "WHERE post_id < ? ORDER BY post_id DESC LIMIT " + PAGE_LIMIT, prevPostId);
    }

    public List<PostDto> search(final String query) {
        return db.findAll(PostDto.class, "SELECT post_id AS index, created, content FROM search_index " +
                "WHERE document @@ plainto_tsquery('english', ?) " +
                "ORDER BY ts_rank(document, plainto_tsquery('english', ?)) DESC LIMIT 50", query, query);
    }

    public PostDto random() {
        return db.findUnique(PostDto.class, "SELECT content, created FROM posts OFFSET floor(random() * (SELECT COUNT(*) FROM posts)) LIMIT 1");
    }

    public long count() {
        return db.findUniqueLong("SELECT count(1) FROM posts");
    }

    public long createPost(PostBean post) {
        long id = db.findUniqueLong("INSERT INTO posts (ip, original, content) VALUES (?, ?, ?) RETURNING post_id",
                post.getIp(), post.getOriginal(), post.getContent());
        new Thread(() -> {
            db.update("REFRESH MATERIALIZED VIEW search_index");
        }).start();

        return id;
    }
}
