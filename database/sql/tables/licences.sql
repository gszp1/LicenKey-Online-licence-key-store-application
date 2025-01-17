CREATE TABLE licences (
    licence_id BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(128) UNIQUE NOT NULL,
    developer VARCHAR(128) NOT NULL,
    "description" TEXT,
    price DECIMAL(10, 2) NOT NULL,
    available_for_sale BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (type_id) BIGINT REFERENCES licence_type(type_id) ON DELETE RESTRICT,
    FOREIGN KEY (category_id) BIGINT REFERENCES categories(category_id) ON DELETE RESTRICT,
    FOREIGN KEY (publisher_id) BIGINT REFERENCES publishers(publisher_id) ON DELETE RESTRICT,
    FOREIGN KEY (platform_id) BIGINT REFERENCES platforms(platform_id) ON DELETE RESTRICT,
    FOREIGN KEY (service_id) BIGINT REFERENCES services(service_id) ON DELETE SET NULL,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON licences
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();
