CREATE TABLE keys (
    key_id BIGSERIAL PRIMARY KEY, 
    expired BOOLEAN NOT NULL DEFAULT VALUE FALSE,
    user_id BIGINT,
    licence_id BIGINT,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    FOREIGN KEY (user_id) BIGINT REFERENCES users(user_id) ON DELETE RESTRICT,
    FOREIGN KEY (licence_id) BIGINT REFERENCES licences(licence_id) ON DELETE RESTRICT

);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON keys
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();
