CREATE TABLE posts
(
  post_id  SERIAL                      NOT NULL,
  topic_id BIGINT                      NOT NULL,
  user_id  BIGINT                      NOT NULL,
  content  TEXT                        NOT NULL,
  original TEXT                        NOT NULL,
  modified TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  created  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  CONSTRAINT posts_pk PRIMARY KEY (post_id),
  CONSTRAINT posts_topic_fk FOREIGN KEY (topic_id)
  REFERENCES topics (topic_id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT posts_user_fk FOREIGN KEY (user_id)
  REFERENCES users (user_id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
) WITH (
OIDS = FALSE
);
ALTER TABLE posts
OWNER TO sadbeast;

INSERT INTO posts (topic_id, user_id, content, original) VALUES (1, 1, '<p>This is definitely a test</p>',
  'This is definitely a test');
INSERT INTO posts (topic_id, user_id, content, original) VALUES (1, 2, '<p>Sure is.</p>',
                                                                 'Sure is.');
UPDATE topics SET modified = now() WHERE topic_id = 1;

INSERT INTO posts (topic_id, user_id, content, original) VALUES (2, 2, '<p>Isn''t this just a <strong>copy</strong> of SA?</p>',
                                                                 'Isn''t this just a *copy* of SA?');
UPDATE topics SET modified = now() WHERE topic_id = 2;

CREATE MATERIALIZED VIEW search_index AS
  SELECT
    post_id,
    created,
    content,
    setweight(to_tsvector('english', content), 'A') ||
    setweight(to_tsvector('english', original), 'B') AS document
  FROM posts;

CREATE INDEX fts_posts_idx ON search_index USING GIN (document);
