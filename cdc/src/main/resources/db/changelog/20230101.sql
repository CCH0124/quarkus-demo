CREATE TABLE movie (
   id UUID DEFAULT uuid_generate_v4() NOT NULL,
   name VARCHAR(20) NOT NULL,
   director VARCHAR(20) NOT NULL,
   genre VARCHAR(20) NOT NULL,
   CONSTRAINT pk_movie PRIMARY KEY (id)
);
