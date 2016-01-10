CREATE TABLE topics
(
  topic_id     SERIAL                      NOT NULL,
  user_id      BIGINT                      NOT NULL,
  last_user_id BIGINT                      NOT NULL,
  title        CHARACTER VARYING(128)      NOT NULL,
  handle       CHARACTER VARYING(128)      NOT NULL,
  posts        INT                         NOT NULL DEFAULT 1,
  modified     TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  created      TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  CONSTRAINT topics_pk PRIMARY KEY (topic_id),
  CONSTRAINT topics_users_fk FOREIGN KEY (user_id)
  REFERENCES users (user_id) MATCH SIMPLE
  ON UPDATE RESTRICT ON DELETE RESTRICT
) WITH (
OIDS = FALSE
);
ALTER TABLE topics
OWNER TO sadbeast;

INSERT INTO topics (user_id, last_user_id, title, handle, posts) VALUES (1, 2, 'Test Post', 'test-content', 2);
INSERT INTO topics (user_id, last_user_id, title, handle) VALUES (2, 2, 'This is terrible', 'this-is-terrible');
