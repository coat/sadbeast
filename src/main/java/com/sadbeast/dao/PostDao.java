package com.sadbeast.dao;

import com.sadbeast.dto.PostDto;
import com.sadbeast.web.beans.PostBean;
import org.dalesbred.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;
import java.util.List;

import static org.dalesbred.query.SqlQuery.namedQuery;

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
        long id = db.findUniqueLong(namedQuery("INSERT INTO posts (topic_id, user_id, original, content, created, modified) " +
                "VALUES (:topicId, :userId, :original, :content, :created, :created) RETURNING post_id",
                post));
        db.update(namedQuery("UPDATE topics SET modified = :created, posts = posts + 1 WHERE topic_id = :topicId", post));
        new Thread(() -> {
            db.update("REFRESH MATERIALIZED VIEW search_index");
        }).start();

        return id;
    }

    public List<PostDto> findPostsByTopic(final Long topicId) {
        return db.findAll(PostDto.class, "SELECT u.username AS author, u.created AS authorCreated, p.content, p.created FROM posts p " +
                "JOIN users u ON p.user_id = u.user_id WHERE topic_id = ? ORDER BY p.created", topicId);
    }
}
