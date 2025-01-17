CREATE TABLE services (
    service_id BIGSERIAL PRIMARY KEY,
    api_url VARCHAR(2083) NOT NULL,  /* Currently max length of url*/
    FK_publisher_id BIGINT,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    FOREIGN KEY (FK_publisher_id) REFERENCES publishers(publisher_id) ON DELETE CASCADE
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON services
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();