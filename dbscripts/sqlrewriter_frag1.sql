DROP TABLE IF EXISTS medinfo;

CREATE TABLE medinfo (
    multiid   integer PRIMARY KEY NOT NULL,
    id          integer NOT NULL,
    firstname   text NOT NULL,
    lastname    text NOT NULL
);

INSERT INTO medinfo VALUES (1001, 1, 'Ivo-1', 'Ivic-1');
INSERT INTO medinfo VALUES (1002, 2, 'Ana-1', 'Anic-1');
INSERT INTO medinfo VALUES (1003, 3, 'Ivo-1', 'Ivic-1');
INSERT INTO medinfo VALUES (1004, 4, 'Ana-1', 'Anic-1');
INSERT INTO medinfo VALUES (1005, 5, 'Pero-1', 'Peric-1');
