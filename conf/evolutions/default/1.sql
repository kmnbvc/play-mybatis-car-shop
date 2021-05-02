-- initial schema

-- !Ups

CREATE DOMAIN year_int AS smallint
    CHECK(VALUE >= 1900 AND VALUE <= 9999);

CREATE TABLE IF NOT EXISTS brands (
                        name varchar(64) NOT NULL,
                        country varchar(3) NOT NULL,
                        PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS models (
                        name varchar(64) NOT NULL,
                        year_manufacture_began year_int NOT NULL,
                        year_manufacture_ended year_int,
                        PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS positions (
                        id uuid NOT NULL,
                        brand_name varchar(64) NOT NULL,
                        model_name varchar(64) NOT NULL,
                        year_of_manufacture year_int NOT NULL,
                        run int NOT NULL CHECK(run >= 0),
                        price int NOT NULL CHECK(price > 0),
                        PRIMARY KEY (id),
                        FOREIGN KEY (brand_name) REFERENCES brands(name),
                        FOREIGN KEY (model_name) REFERENCES models(name),
);

-- !Downs

DROP TABLE IF EXISTS brands;
DROP TABLE IF EXISTS models;
DROP TABLE IF EXISTS positions;
DROP DOMAIN year_int;
