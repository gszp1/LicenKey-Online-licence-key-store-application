CREATE TYPE user_status AS ENUM ('active', 'deactivated', 'banned');
CREATE TYPE user_role AS ENUM ('user', 'admin');

CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    email varchar(320) NOT NULL UNIQUE, /*RFC5321, RFC5322 local-part->64, domain->255 */
    password_hash varchar(256) NOT NULL, /* 256 bytes Argon2*/
    username varchar(100),
    first_name varchar(50),
    last_name varchar(50),
    account_status user_status NOT NULL DEFAULT 'active',
    account_role user_role NOT NULL DEFAULT 'user',
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    deactivation_date TIMESTAMPTZ,
    update_date TIMESTAMPTZ NOT NULL DEFAULT now()
);

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO users_service_db_user;
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO users_service_db_user;

GRANT USAGE ON SCHEMA public TO users_service_db_user;

