CREATE TABLE orders (
    FK_user_id BIGINT,
    FK_licence_id BIGINT,
    order_id UUID NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL CHECK(unit_price >= 0),
    quantity INT NOT NULL CHECK(quantity > 0),
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (FK_user_id, FK_licence_id, order_id),
    FOREIGN KEY (FK_user_id) REFERENCES users(user_id) ON DELETE RESTRICT,
    FOREIGN KEY (FK_licence_id) REFERENCES licences(licence_id) ON DELETE RESTRICT
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON orders
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();