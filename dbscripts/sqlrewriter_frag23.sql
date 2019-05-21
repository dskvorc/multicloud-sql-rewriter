DROP TABLE IF EXISTS medinfo;

CREATE TABLE medinfo (
    multiid integer PRIMARY KEY NOT NULL,
    city    text NOT NULL,
    illness text NOT NULL
);

INSERT INTO medinfo VALUES (1001, 'Zagreb-23', 'Flu-23');
INSERT INTO medinfo VALUES (1002, 'Split-23', 'Hypertension-23');
INSERT INTO medinfo VALUES (1003, 'Rijeka-23', 'Asthma-23');
INSERT INTO medinfo VALUES (1004, 'Osijek-23', 'Migraine-23');
INSERT INTO medinfo VALUES (1005, 'Pula-23', 'Flu-23');
