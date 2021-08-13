CREATE TABLE sscusers (
  creationdate timestamp NULL,
  "name" varchar(32) NULL,
  title varchar(128) NULL,
  "password" bytea NULL,
  tenantid uuid NULL,
  "blocked" bool NULL,
  id uuid NOT NULL,
  phone varchar(50) NULL,
  email varchar(50) NULL
);