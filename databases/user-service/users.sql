CREATE TYPE user_status AS ENUM ('active', 'deactivated', 'banned');
CREATE TYPE user_role AS ENUM ('user', 'admin');

CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    identifier uuid DEFAULT gen_random_uuid(),
    email varchar(320) NOT NULL UNIQUE, /*RFC5321, RFC5322 local-part->64, domain->255 */
    password_hash varchar(256) NOT NULL, /* 256 bytes Argon2*/
    username varchar(100) NOT NULL UNIQUE,
    first_name varchar(50),
    last_name varchar(50),
    user_status user_status NOT NULL DEFAULT 'active',
    user_role user_role NOT NULL DEFAULT 'user',
    active BOOLEAN DEFAULT false,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    deactivation_date TIMESTAMPTZ,
    update_date TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE OR REPLACE FUNCTION refresh_update_date()
RETURNS TRIGGER AS $$
BEGIN
    NEW.update_date = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO users_service_db_user;
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO users_service_db_user;

GRANT USAGE ON SCHEMA public TO users_service_db_user;

