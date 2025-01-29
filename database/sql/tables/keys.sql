CREATE TABLE keys (
    key_id BIGSERIAL PRIMARY KEY, 
    expired BOOLEAN NOT NULL DEFAULT FALSE,
    key_code VARCHAR(100) UNIQUE,
    order_id UUID NOT NULL,
    FK_licence_id BIGINT,
    FK_user_id BIGINT,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    FOREIGN KEY (FK_licence_id) REFERENCES licences(licence_id) ON DELETE RESTRICT,
    FOREIGN KEY (FK_user_id) REFERENCES users(user_id) ON DELETE RESTRICT
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON keys
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();
