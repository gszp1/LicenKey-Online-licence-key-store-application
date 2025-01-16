CREATE TABLE licence_types (
    type_id BIGSERIAL PRIMARY KEY,
    duration_days INTEGER,
    'name' VARCHAR(50) NOT NULL DEFAULT 'lifetime',
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON licences
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();