# USERS SERVICE

## Overview ##

Micro-service for larger kubernetes-managed application. This service handles storing users data, authentication and authorisation.

## [Database](./database/) ##

Name: users_service_db

Custom enum types:
1. user_status: _active, deactivated, banned_
2. user_role: _user, admin_

Table users schema:

| Field   | Type | Constraints |
|---------|------|-------------|
| user_id | BIGSERIAL | PK |
| email   | VARCHAR(320) | NOT NULL UNIQUE |
| password_hash | VARCHAR(256) | NOT NULL |
| username | VARCHAR(100) | |
| first_name | VARCHAR(50) | |
| last_name | VARCHAR(50) | |
| account_status | user_status | NOT NULL |
| account_role | user_role | NOT NULL |
| creation_date | TIMESTAMPTZ | NOT NULL |
| deactivation_date | TIMESTAMPTZ | |
| update_date | TIMESTAMPTZ | NOT NULL |


## Tech Stack ##
1. Java
2. Spring Boot
3. JSON Web Token
4. PostgreSQL
5. Docker