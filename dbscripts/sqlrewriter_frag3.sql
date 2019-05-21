DROP TABLE IF EXISTS medinfo;

CREATE TABLE medinfo (
    multiid integer PRIMARY KEY NOT NULL,
    illness text NOT NULL
);

INSERT INTO medinfo VALUES (1001, 'Flu-3');
INSERT INTO medinfo VALUES (1002, 'Hypertension-3');
INSERT INTO medinfo VALUES (1003, 'Asthma-3');
INSERT INTO medinfo VALUES (1004, 'Migraine-3');
INSERT INTO medinfo VALUES (1005, 'Flu-3');
