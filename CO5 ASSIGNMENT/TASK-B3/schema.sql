-- Database Schema for Campus Transport Management
CREATE DATABASE IF NOT EXISTS campus_db;
USE campus_db;

DROP TABLE IF EXISTS bus_pass;
DROP TABLE IF EXISTS users;

CREATE TABLE bus_pass (
    pass_id VARCHAR(20) PRIMARY KEY,
    reg_no VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    route_no INT NOT NULL,
    amount DOUBLE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users (
    id VARCHAR(50) PRIMARY KEY,
    pwd VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'STUDENT'
);

INSERT INTO users (id, pwd, role) VALUES ('admin', 'admin123', 'ADMIN');
