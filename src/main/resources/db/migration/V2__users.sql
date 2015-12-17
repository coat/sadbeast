CREATE TABLE users
(
  user_id    SERIAL                      NOT NULL,
  username   CHARACTER VARYING(64)       NOT NULL,
  password   CHARACTER(60)               NOT NULL,
  email      CHARACTER VARYING(64)       NOT NULL,
  first_name CHARACTER VARYING(64)       NOT NULL,
  last_name  CHARACTER VARYING(64)       NULL,
  modified   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  created    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  CONSTRAINT users_pk PRIMARY KEY (user_id),
  CONSTRAINT users_username_uq UNIQUE (username)
)
WITH (
OIDS = FALSE
);
ALTER TABLE users
OWNER TO sadbeast;

CREATE TABLE roles
(
  role_id  SERIAL                      NOT NULL,
  key      CHARACTER VARYING(16)       NOT NULL,
  name     CHARACTER VARYING(64)       NOT NULL,
  modified TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  created  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  CONSTRAINT roles_pk PRIMARY KEY (role_id),
  CONSTRAINT roles_name_uq UNIQUE (name)
)
WITH (
OIDS = FALSE
);
ALTER TABLE roles
OWNER TO sadbeast;

INSERT INTO roles (key, name) VALUES ('admin', 'SadBeast Admin');

CREATE TABLE permissions
(
  permission_id SERIAL                      NOT NULL,
  name          CHARACTER VARYING(64)       NOT NULL,
  modified      TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  created       TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  CONSTRAINT permissions_pk PRIMARY KEY (permission_id),
  CONSTRAINT permissions_name_uq UNIQUE (name)
)
WITH (
OIDS = FALSE
);
ALTER TABLE permissions
OWNER TO sadbeast;

INSERT INTO permissions (name) VALUES ('Manage SadBeast');

CREATE TABLE role_permissions
(
  role_permission_id SERIAL                      NOT NULL,
  role_id            BIGINT                      NOT NULL,
  permission_id      BIGINT                      NOT NULL,
  modified           TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  created            TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  CONSTRAINT role_permissions_pk PRIMARY KEY (role_permission_id),
  CONSTRAINT roles_permissions_uq UNIQUE (role_id, permission_id),
  CONSTRAINT role_permissions_roles_fk FOREIGN KEY (role_id)
  REFERENCES roles (role_id) MATCH SIMPLE
  ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT role_permissions_permissions_fk FOREIGN KEY (permission_id)
  REFERENCES permissions (permission_id) MATCH SIMPLE
  ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
OIDS = FALSE
);
ALTER TABLE role_permissions
OWNER TO sadbeast;

INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 1);

CREATE TABLE user_roles
(
  user_role_id SERIAL                      NOT NULL,
  user_id      BIGINT                      NOT NULL,
  role_id      BIGINT                      NOT NULL,
  modified     TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  created      TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT clock_timestamp(),
  CONSTRAINT user_roles_pk PRIMARY KEY (user_role_id),
  CONSTRAINT user_roles_user_fk FOREIGN KEY (user_id)
  REFERENCES users (user_id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_roles_role_fk FOREIGN KEY (role_id)
  REFERENCES roles (role_id) MATCH SIMPLE
  ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
OIDS = FALSE
);
ALTER TABLE user_roles
OWNER TO sadbeast;

/* Password is: admin */
INSERT INTO users (username, password, email, first_name, last_name) VALUES
  ('coat', '$2a$10$vbYq3Zmc3aJtlCLBSrpV6.MtIFd6.Z10fJ9ofGpYq3mR4XjiFfe4.', 'kent.smith@gmail.com', 'Kent', 'Smith');
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
