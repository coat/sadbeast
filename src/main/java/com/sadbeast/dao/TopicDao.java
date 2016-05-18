package com.sadbeast.dao;

import com.sadbeast.dto.NewTopicDto;
import com.sadbeast.dto.TopicDto;
import org.dalesbred.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

import static org.dalesbred.query.SqlQuery.namedQuery;

@Singleton
public class TopicDao {
    private final Database db;

    @Inject
    public TopicDao(final Database db) {
        this.db = db;
    }

    public List<TopicDto> getTopicSummaries() {
        return db.findAll(TopicDto.class, "SELECT" +
                " t.topic_id AS id," +
                " t.title," +
                " t.modified," +
                " a.username AS author," +
                " l.username AS lastUser," +
                " t.handle," +
                " t.posts AS postCount" +
                " FROM topics t JOIN users a ON t.user_id = a.user_id" +
                " JOIN users l ON t.last_user_id = l.user_id" +
                " ORDER BY t.modified DESC");
    }

    public Optional<TopicDto> getTopic(final Long topicId) {
        return db.findOptional(TopicDto.class, "SELECT t.topic_id AS id, t.title, t.handle " +
                " FROM topics t WHERE t.topic_id = ?", topicId);
    }

    public void create(final NewTopicDto topic) {
        db.withVoidTransaction(tx -> {
            Long id = db.findUniqueLong(namedQuery("INSERT INTO topics (user_id, last_user_id, title, handle, posts) " +
                    " VALUES (:userId, :userId, :title, :handle, 1) RETURNING topic_id", topic));
            db.update("INSERT INTO posts (topic_id, user_id, content, original) VALUES (?, ?, ?, ?)",
                    id, topic.getUserId(), topic.getContent(), topic.getContent());
        });
    }
}
