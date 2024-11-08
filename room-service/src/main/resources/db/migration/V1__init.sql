-- V1__init.sql
CREATE TABLE rooms (
       id BIGSERIAL PRIMARY KEY,
       room_name VARCHAR(255) NOT NULL,
       capacity INT NOT NULL,
       features VARCHAR(255),
       availability BOOLEAN NOT NULL
);
