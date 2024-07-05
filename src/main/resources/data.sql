INSERT INTO USERS (ID, FULL_NAME, USERNAME, PASSWORD, ROLE)
SELECT *
FROM (
         VALUES (UUID(), 'Breno Magrani', 'brinudo', '$2a$10$2mAwEXJ1SaMWWpI/Unj/1e7Nkpz7pP4wWAls4zZ5WIE.366iMUilC',
                 'ADMIN')
         ) AS source_data
WHERE NOT EXISTS (SELECT 1 FROM USERS);

INSERT INTO ALBUM (ID,NAME,PAGE_NUMBER)
SELECT *
FROM (
         VALUES (UUID(), 'Album Copa do Mundo 2018',14)
         ) AS source_data
WHERE NOT EXISTS (SELECT 1 FROM ALBUM);