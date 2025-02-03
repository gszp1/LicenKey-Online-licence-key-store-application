INSERT INTO platforms ("name", home_page)
VALUES 
    ('Windows', 'https://www.microsoft.com/windows'),
    ('macOS', 'https://www.apple.com/macos'),
    ('Linux', 'https://www.linux.org/')
RETURNING platform_id;

INSERT INTO publishers ("name", home_page)
VALUES 
    ('Adobe', 'https://www.adobe.com'),
    ('Microsoft', 'https://www.microsoft.com'),
    ('Apple', 'https://www.apple.com')
RETURNING publisher_id;

INSERT INTO services (api_url, FK_publisher_id)
VALUES 
    ('http://key-gen-service-first-svc.key-gen-ns.svc.cluster.local/api/key', 1),
    ('http://key-gen-service-second-svc.key-gen-ns.svc.cluster.local/api/key', 2),
    ('http://key-gen-service-third-svc.key-gen-ns.svc.cluster.local/api/key', 3)
RETURNING service_id;

INSERT INTO licence_types ("name", duration_days)
VALUES 
    ('Standard', 365),
    ('Premium', 730)
RETURNING type_id;

INSERT INTO categories ("name")
VALUES 
    ('Productivity'),
    ('Entertainment'),
    ('Development')
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
        'Adobe Photoshop', 
        'Adobe Inc.', 
        'A powerful image editing software.', 
        239.88, 
        NULL, 
        TRUE, 
        1,
        1,
        1,
        1,
        1
    ),
    (
        'Microsoft Office', 
        'Microsoft Corporation', 
        'A suite of productivity applications.', 
        149.99, 
        NULL, 
        FALSE,
        2,
        1,
        2,
        1,
        2
    )
RETURNING licence_id;
