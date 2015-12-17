CREATE TABLE posts
(
  post_id  SERIAL                      NOT NULL,
  content  TEXT                        NOT NULL,
  original TEXT                        NOT NULL,
  ip       CHARACTER VARYING(16)       NULL,
  modified TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  created  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  CONSTRAINT posts_pk PRIMARY KEY (post_id)
) WITH (
OIDS = FALSE
);
ALTER TABLE posts
OWNER TO sadbeast;

CREATE MATERIALIZED VIEW search_index AS
  SELECT
    post_id,
    created,
    content,
    setweight(to_tsvector('english', content), 'A') ||
    setweight(to_tsvector('english', original), 'B') AS document
  FROM posts;

CREATE INDEX fts_posts_idx ON search_index USING GIN (document);
