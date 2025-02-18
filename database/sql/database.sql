CREATE OR REPLACE FUNCTION refresh_update_date()
RETURNS TRIGGER AS $$
BEGIN
    NEW.update_date = CURRENT_TIMESTAMP;
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
    user_status varchar(20) NOT NULL DEFAULT 'active',
    user_role varchar(20) NOT NULL DEFAULT 'user',
    active BOOLEAN NOT NULL DEFAULT FALSE,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deactivation_date TIMESTAMPTZ,
    update_date TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHECK (user_status IN ('active', 'banned', 'deactivated')),
    CHECK (user_role IN ('admin', 'user'))
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
    image_url VARCHAR(256),
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


CREATE TABLE confirmed_carts(
    FK_user_id BIGINT,
    FK_licence_id BIGINT,
    order_identifier UUID NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    price DECIMAL(10, 2) NOT NULL,
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    update_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (FK_user_id, FK_licence_id, order_identifier),
    FOREIGN KEY (FK_user_id) REFERENCES users(user_id) ON DELETE RESTRICT,
    FOREIGN KEY (FK_licence_id) REFERENCES licences(licence_id) ON DELETE RESTRICT
);

CREATE TRIGGER refresh_update_date_trg
BEFORE UPDATE ON confirmed_carts
FOR EACH ROW
EXECUTE FUNCTION refresh_update_date();

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO licen_key_user;
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO licen_key_user;

GRANT USAGE ON SCHEMA public TO licen_key_user;

INSERT INTO platforms ("name", home_page)
VALUES 
    ('GameSphere', 'https://example.com/gamesphere'),
    ('AppCentral', 'https://example.com/appcentral'),
    ('DigitalArcade', 'https://example.com/digitalarcade'),
    ('SoftMart', 'https://example.com/softmart'),
    ('CloudStore', 'https://example.com/cloudstore')
RETURNING platform_id;


INSERT INTO publishers ("name", home_page)
VALUES 
    ('TechSoft', 'https://techsoft.example.com'),
    ('Innovatech', 'https://innovatech.example.com'),
    ('FutureWare', 'https://futureware.example.com'),
    ('Alpha Systems', 'https://alphasystems.example.com'),
    ('NovaCorp', 'https://novacorp.example.com')
RETURNING publisher_id;


INSERT INTO services (api_url, FK_publisher_id)
VALUES 
    ('http://key-gen-service-first-svc.key-gen-ns.svc.cluster.local:9002/api/key', 1),
    ('http://key-gen-service-second-svc.key-gen-ns.svc.cluster.local:9002/api/key', 2),
    ('http://key-gen-service-third-svc.key-gen-ns.svc.cluster.local:9002/api/key', 3)
RETURNING service_id;


INSERT INTO licence_types ("name", duration_days)
VALUES 
    ('Lifetime', 0),       
    ('Annual', 365),
    ('Semiannual', 182),
    ('Quarterly', 90),
    ('Monthly', 30)
RETURNING type_id;

INSERT INTO categories ("name")
VALUES 
    ('Productivity'),
    ('Entertainment'),
    ('Development'),
    ('Security'),
    ('Education')
RETURNING category_id;

INSERT INTO licences (
    "name", 
    developer, 
    "description", 
    price, 
    image_url, 
    available_for_sale, 
    FK_licence_type_id, 
    FK_category_id, 
    FK_publisher_id, 
    FK_platform_id, 
    FK_service_id
)
VALUES 
    (
        'Advanced Photo Editor', 
        'TechSoft', 
        'A robust photo editing application for professionals.', 
        99.99, 
        NULL, 
        TRUE, 
        1,
        1,
        1,
        1,
        1
    ),
    (
        'Office Suite Pro', 
        'Innovatech', 
        'An integrated suite for office productivity and collaboration.', 
        149.99, 
        NULL, 
        TRUE,
        2,
        1,
        2,
        2,
        2
    ),
    (
        'DevStudio IDE', 
        'FutureWare', 
        'A comprehensive development environment for building applications.', 
        199.99, 
        NULL, 
        TRUE, 
        3,
        3,
        3,
        3,
        3
    ),
    (
        'Secure Antivirus', 
        'Alpha Systems', 
        'Advanced antivirus solution for protecting systems and data.', 
        79.99, 
        NULL, 
        FALSE,
        4,
        4,
        4,
        4,
        1
    ),
    (
        'Digital Learning Platform', 
        'NovaCorp', 
        'An interactive platform designed for modern educational needs.', 
        59.99, 
        NULL, 
        TRUE,
        5,
        5,
        5,
        5,
        2
    )
RETURNING licence_id;


