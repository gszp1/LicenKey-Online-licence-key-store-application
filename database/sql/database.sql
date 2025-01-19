CREATE TYPE user_status AS ENUM ('active', 'deactivated', 'banned');
CREATE TYPE user_role AS ENUM ('user', 'admin');
-- CREATE TYPE licence_type AS ENUM ('monthly', 'trial', 'yearly', 'lifetime', 'weekly', 'daily')

CREATE OR REPLACE FUNCTION refresh_update_date()
RETURNS TRIGGER AS $$
BEGIN
    NEW.update_date = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TABLE platforms (
    platform_id BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(128) UNIQUE NOT NULL,
    home_page VARCHAR(2083),
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON platforms
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();


CREATE TABLE licence_types (
    type_id BIGSERIAL PRIMARY KEY,
    duration_days INTEGER,
    "name" VARCHAR(50) UNIQUE NOT NULL,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON licence_types
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();

CREATE TABLE categories (
    category_id BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(100) NOT NULL UNIQUE,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON categories
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();


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

CREATE TABLE publishers (
    publisher_id BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(100) UNIQUE NOT NULL,
    home_page VARCHAR(2083),
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON publishers
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();

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

CREATE TABLE licences (
    licence_id BIGSERIAL PRIMARY KEY,
    "name" VARCHAR(128) UNIQUE NOT NULL,
    developer VARCHAR(128) NOT NULL,
    "description" TEXT,
    price DECIMAL(10, 2) NOT NULL,
    available_for_sale BOOLEAN NOT NULL DEFAULT TRUE,
    FK_licence_type_id BIGINT,
    FK_category_id BIGINT,
    FK_publisher_id BIGINT,
    FK_platform_id BIGINT,
    FK_service_id BIGINT,
    FOREIGN KEY (FK_licence_type_id) REFERENCES licence_types(type_id) ON DELETE RESTRICT,
    FOREIGN KEY (FK_category_id) REFERENCES categories(category_id) ON DELETE RESTRICT,
    FOREIGN KEY (FK_publisher_id) REFERENCES publishers(publisher_id) ON DELETE RESTRICT,
    FOREIGN KEY (FK_platform_id) REFERENCES platforms(platform_id) ON DELETE RESTRICT,
    FOREIGN KEY (FK_service_id) REFERENCES services(service_id) ON DELETE SET NULL,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON licences
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();


CREATE TABLE orders (
    FK_user_id BIGINT,
    FK_licence_id BIGINT,
    unit_price DECIMAL(10,2) NOT NULL CHECK(unit_price >= 0),
    quantity INT NOT NULL CHECK(quantity > 0),
    order_id UUID NOT NULL,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (FK_user_id, FK_licence_id),
    FOREIGN KEY (FK_user_id) REFERENCES users(user_id) ON DELETE RESTRICT,
    FOREIGN KEY (FK_licence_id) REFERENCES licences(licence_id) ON DELETE RESTRICT
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON orders
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();

CREATE TABLE shopping_carts (
    FK_user_id BIGINT,
    FK_licence_id BIGINT,
    quantity INT NOT NULL CHECK (quantity > 0),
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (FK_user_id, FK_licence_id),
    FOREIGN KEY (FK_user_id) REFERENCES users(user_id) ON DELETE RESTRICT,
    FOREIGN KEY (FK_licence_id) REFERENCES licences(licence_id) ON DELETE RESTRICT
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON shopping_carts
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();

CREATE TABLE keys (
    key_id BIGSERIAL PRIMARY KEY, 
    expired BOOLEAN NOT NULL DEFAULT FALSE,
    key_code VARCHAR(20) NOT NULL UNIQUE,
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


GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO licen_key_user;
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO licen_key_user;

GRANT USAGE ON SCHEMA public TO licen_key_user;

