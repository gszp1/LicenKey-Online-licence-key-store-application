INSERT INTO platforms ("name", home_page)
VALUES 
    ('SoftwareSphere', 'https://example.com/gamesphere'),
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
    ('http://key-gen-service-second-svc.key-gen-ns.svc.cluster.local:9012/api/key', 2),
    ('http://key-gen-service-third-svc.key-gen-ns.svc.cluster.local:9022/api/key', 3)
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
        'PixelCraft Studios', 
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
        'OfficePro Solutions', 
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
        'DevWizard Labs', 
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
        'SecureNet Systems', 
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
        'EduTech Innovations', 
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
