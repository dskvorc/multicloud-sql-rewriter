DROP TABLE IF EXISTS medinfo2;

CREATE TABLE medinfo2 (
    multiid integer PRIMARY KEY NOT NULL,
    age     integer NOT NULL
);

INSERT INTO medinfo2 VALUES (1001, 35);
INSERT INTO medinfo2 VALUES (1002, 48);
INSERT INTO medinfo2 VALUES (1003, 70);
INSERT INTO medinfo2 VALUES (1004, 56);
INSERT INTO medinfo2 VALUES (1005, 21);