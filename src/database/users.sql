CREATE TABLE users (
    user_id BIGSERIAL,
    email varchar(320), /*RFC5321, RFC5322 local-part->64, domain->255 */
    password_hash varchar(256), /* 256 bytes Argon2*/
    username varchar(100),
    first_name varchar(50),
    last_name varchar(50),
);

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO users_service_db_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO users_service_db_user;
GRANT USAGE ON SCHEMA public TO users_service_db_user;

