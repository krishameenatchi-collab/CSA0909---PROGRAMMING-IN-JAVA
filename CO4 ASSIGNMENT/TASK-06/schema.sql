-- TASK-06: schema.sql
CREATE DATABASE IF NOT EXISTS campus_db;
USE campus_db;

DROP TABLE IF EXISTS event_registrations;

CREATE TABLE event_registrations (
    registration_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(20) NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    event_id VARCHAR(50) NOT NULL,
    registration_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_student_event UNIQUE (student_id, event_id)
);
