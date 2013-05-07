------------------------------
-- pgDesigner 1.2.17
--
-- Project    : GasAnalyzer
-- Date       : 05/07/2013 14:32:08.738
-- Description: 
------------------------------

CREATE ROLE "GasAnalyzer" LOGIN
  ENCRYPTED PASSWORD 'md52b2f56b2a3685bea379c2361dc12d0f6'
  SUPERUSER INHERIT CREATEDB CREATEROLE;

-- Database: "GasAnalyzer"
CREATE DATABASE "GasAnalyzer"
  WITH OWNER = "GasAnalyzer"
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'pl_PL.UTF-8'
       LC_CTYPE = 'pl_PL.UTF-8'
       CONNECTION LIMIT = -1;

-- Start Table's declaration
CREATE TABLE "survey" (
"id" serial4 NOT NULL,
"timestamp" timestamp NOT NULL,
"name" varchar(50) NOT NULL,
"load" double precision,
"special_conditions" varchar(100),
"comment" varchar(100),
"object_id" int4 NOT NULL,
"application_user_id" int4 NOT NULL
) WITH OIDS;
ALTER TABLE "survey" ADD CONSTRAINT "survey_pk" PRIMARY KEY("id");

CREATE TABLE "place" (
"id" serial4 NOT NULL,
"name" varchar(30),
"city" varchar(30),
"post_code" varchar(10),
"address" varchar(30)
) WITH OIDS;
ALTER TABLE "place" ADD CONSTRAINT "place_pk" PRIMARY KEY("id");

CREATE TABLE "application_user" (
"id" serial4 NOT NULL,
"name" varchar(30) NOT NULL,
"surname" varchar(30) NOT NULL,
"degree_id" int4 NOT NULL,
"function_id" int4 NOT NULL
) WITH OIDS;
ALTER TABLE "application_user" ADD CONSTRAINT "application_user_pk" PRIMARY KEY("id");

CREATE TABLE "degree" (
"id" serial4 NOT NULL,
"name" varchar(30) NOT NULL
) WITH OIDS;
ALTER TABLE "degree" ADD CONSTRAINT "degree_pk" PRIMARY KEY("id");

CREATE TABLE "function" (
"id" serial4 NOT NULL,
"name" varchar(30) NOT NULL
) WITH OIDS;
ALTER TABLE "function" ADD CONSTRAINT "function_pk" PRIMARY KEY("id");

CREATE TABLE "measurement_set" (
"id" serial4 NOT NULL,
"timestamp" timestamp NOT NULL,
"device_id" int4 NOT NULL,
"measurement_snapshot_id" int4
) WITH OIDS;
ALTER TABLE "measurement_set" ADD CONSTRAINT "measurement_set_pk" PRIMARY KEY("id");

CREATE TABLE "device_type" (
"id" serial4 NOT NULL,
"type" varchar(30) NOT NULL,
"document" bytea
) WITH OIDS;
ALTER TABLE "device_type" ADD CONSTRAINT "device_type_pk" PRIMARY KEY("id");

CREATE TABLE "measurement" (
"id" serial4 NOT NULL,
"value" double precision NOT NULL,
"measurement_set_id" int4 NOT NULL,
"measurement_variable_id" int4 NOT NULL,
"measurement_dimension_id" int4 NOT NULL
) WITH OIDS;
ALTER TABLE "measurement" ADD CONSTRAINT "measurement_pk" PRIMARY KEY("id");

CREATE TABLE "measurement_variable" (
"id" serial4 NOT NULL,
"name" varchar(30) NOT NULL
) WITH OIDS;
ALTER TABLE "measurement_variable" ADD CONSTRAINT "measurement_variable_pk" PRIMARY KEY("id");

CREATE TABLE "measurement_dimension" (
"id" serial4 NOT NULL,
"name" varchar(30) NOT NULL
) WITH OIDS;
ALTER TABLE "measurement_dimension" ADD CONSTRAINT "measurement_dimension_pk" PRIMARY KEY("id");

CREATE TABLE "object" (
"id" serial4 NOT NULL,
"name" varchar(50) NOT NULL,
"description" varchar(100),
"place_id" int4 NOT NULL
) WITH OIDS;
ALTER TABLE "object" ADD CONSTRAINT "object_pk" PRIMARY KEY("id");

CREATE TABLE "survey_section" (
"id" serial4 NOT NULL,
"name" varchar(30) NOT NULL
) WITH OIDS;
ALTER TABLE "survey_section" ADD CONSTRAINT "survey_section_pk" PRIMARY KEY("id");

CREATE TABLE "measurement_snapshot" (
"id" serial4 NOT NULL,
"timestamp" timestamp NOT NULL,
"survey_id" int4 NOT NULL,
"comment" varchar(250),
"survey_section_id" int4 NOT NULL
) WITH OIDS;
ALTER TABLE "measurement_snapshot" ADD CONSTRAINT "measurement_snapshot_pk" PRIMARY KEY("id");

CREATE TABLE "device" (
"id" serial4 NOT NULL,
"device_type_id" int4 NOT NULL,
"name" varchar(30) NOT NULL,
"address" int NOT NULL
) WITH OIDS;
ALTER TABLE "device" ADD CONSTRAINT "device_pk" PRIMARY KEY("id");

-- End Table's declaration

-- Start Relation's declaration
ALTER TABLE "application_user" ADD CONSTRAINT "degree_id" FOREIGN KEY ("degree_id") REFERENCES "degree"("id") ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE "application_user" ADD CONSTRAINT "function_id" FOREIGN KEY ("function_id") REFERENCES "function"("id") ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE "survey" ADD CONSTRAINT "application_user_id" FOREIGN KEY ("application_user_id") REFERENCES "application_user"("id") ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE "measurement" ADD CONSTRAINT "measurement_variable_id" FOREIGN KEY ("measurement_variable_id") REFERENCES "measurement_variable"("id") ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE "measurement" ADD CONSTRAINT "measurement_dimension_id" FOREIGN KEY ("measurement_dimension_id") REFERENCES "measurement_dimension"("id") ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE "measurement" ADD CONSTRAINT "measurement_set_id" FOREIGN KEY ("measurement_set_id") REFERENCES "measurement_set"("id") ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE "object" ADD CONSTRAINT "place_id" FOREIGN KEY ("place_id") REFERENCES "place"("id") ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE "measurement_snapshot" ADD CONSTRAINT "survey_id" FOREIGN KEY ("survey_id") REFERENCES "survey"("id") ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE "measurement_set" ADD CONSTRAINT "measurement_snapshot_id" FOREIGN KEY ("measurement_snapshot_id") REFERENCES "measurement_snapshot"("id") ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE "device" ADD CONSTRAINT "device_type_id" FOREIGN KEY ("device_type_id") REFERENCES "device_type"("id") ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE "measurement_set" ADD CONSTRAINT "device_id" FOREIGN KEY ("device_id") REFERENCES "device"("id") ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE "measurement_snapshot" ADD CONSTRAINT "survey_section_id" FOREIGN KEY ("survey_section_id") REFERENCES "survey_section"("id") ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE "survey" ADD CONSTRAINT "object_id" FOREIGN KEY ("object_id") REFERENCES "object"("id") ON UPDATE RESTRICT ON DELETE RESTRICT;

-- End Relation's declaration

