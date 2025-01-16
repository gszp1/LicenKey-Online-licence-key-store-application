CREATE TABLE publishers (
    publisher_id BIGSERIAL PRIMARY KEY,
    'name' VARCHAR(100) UNIQUE NOT NULL,
    home_page VARCHAR(2083),
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();