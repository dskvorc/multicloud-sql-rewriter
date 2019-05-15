DROP TABLE IF EXISTS medinfo1;

CREATE TABLE medinfo1 (
    multiid   integer PRIMARY KEY NOT NULL,
    id          integer NOT NULL,
    firstname   text NOT NULL,
    lastname    text NOT NULL
);

INSERT INTO medinfo1 VALUES (1001, 1, 'Ivo', 'Ivic');
INSERT INTO medinfo1 VALUES (1002, 2, 'Ana', 'Anic');
INSERT INTO medinfo1 VALUES (1003, 3, 'Ivo', 'Ivic');
INSERT INTO medinfo1 VALUES (1004, 4, 'Ana', 'Anic');
INSERT INTO medinfo1 VALUES (1005, 5, 'Pero', 'Peric');
