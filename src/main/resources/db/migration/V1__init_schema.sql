CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE employees (
                           id UUID PRIMARY KEY,
                           first_name VARCHAR(255) NOT NULL,
                           last_name VARCHAR(255) NOT NULL,
                           age INTEGER NOT NULL,
                           personnel_number VARCHAR(255) NOT NULL UNIQUE,
                           department VARCHAR(50) NOT NULL,
                           job_description VARCHAR(255) NOT NULL,
                           annual_income INTEGER NOT NULL
);