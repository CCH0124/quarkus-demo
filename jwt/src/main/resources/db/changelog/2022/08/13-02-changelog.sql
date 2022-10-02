-- liquibase formatted sql
CREATE TABLE roles (
   id UUID DEFAULT uuid_generate_v4() NOT NULL,
   name VARCHAR(20) NOT NULL,
   CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE users (
   id UUID DEFAULT uuid_generate_v4() NOT NULL,
   account VARCHAR(30) NOT NULL,
   username VARCHAR(20) NOT NULL,
   email VARCHAR(50) NOT NULL,
   password VARCHAR(120) NOT NULL,
   CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE user_roles (
   role_id UUID DEFAULT uuid_generate_v4() NOT NULL,
   user_id UUID DEFAULT uuid_generate_v4() NOT NULL,
   CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

ALTER TABLE users ADD CONSTRAINT uc_74165e195b2f7b25de690d14a UNIQUE (email);

ALTER TABLE users ADD CONSTRAINT uc_77584fbe74cc86922be2a3560 UNIQUE (username);

ALTER TABLE users ADD CONSTRAINT uc_b55d041df7659f06d1a8f1225 UNIQUE (account);

ALTER TABLE user_roles ADD CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_roles ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id);