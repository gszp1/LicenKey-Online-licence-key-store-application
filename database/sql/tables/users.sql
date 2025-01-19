CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    email varchar(320) NOT NULL UNIQUE, /*RFC5321, RFC5322 local-part->64, domain->255 */
    password_hash varchar(60) NOT NULL, /* 256 bytes BCrypt*/
    username varchar(100) NOT NULL UNIQUE,
    first_name varchar(50),
    last_name varchar(50),
    user_status user_status NOT NULL DEFAULT 'active',
    user_role user_role NOT NULL DEFAULT 'user',
    active BOOLEAN NOT NULL DEFAULT FALSE,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    deactivation_date TIMESTAMPTZ,
    update_date TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();