DROP TABLE IF EXISTS medinfo3;

CREATE TABLE medinfo3 (
    multiid integer PRIMARY KEY NOT NULL,
    illness text NOT NULL
);

INSERT INTO medinfo3 VALUES (1001, 'Flu');
INSERT INTO medinfo3 VALUES (1002, 'Hypertension');
INSERT INTO medinfo3 VALUES (1003, 'Asthma');
INSERT INTO medinfo3 VALUES (1004, 'Migraine');
INSERT INTO medinfo3 VALUES (1005, 'Flu');
