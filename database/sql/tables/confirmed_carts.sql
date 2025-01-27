CREATE TABLE confirmed_carts(
    FK_user_id BIGINT,
    FK_licence_id BIGINT,
    quantity INT NOT NULL CHECK (quantity > 0),
    order_identifier UUID NOT NULL,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (FK_user_id, FK_licence_id),
    FOREIGN KEY (FK_user_id) REFERENCES users(user_id) ON DELETE RESTRICT,
    FOREIGN KEY (FK_licence_id) REFERENCES licences(licence_id) ON DELETE RESTRICT
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON confirmed_carts
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();