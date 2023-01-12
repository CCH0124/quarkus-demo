CREATE USER itachi WITH ENCRYPTED PASSWORD '123456' CREATEDB;
--ALTER USER itachi WITH CREATEDB;--
CREATE DATABASE test WITH
        OWNER = itachi
        ENCODING = 'UTF8'
        TABLESPACE = pg_default
        CONNECTION LIMIT = -1;
GRANT ALL PRIVILEGES ON DATABASE test TO itachi;

\c test
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";