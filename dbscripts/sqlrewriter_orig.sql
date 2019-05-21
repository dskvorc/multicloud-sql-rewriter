DROP TABLE IF EXISTS medinfo;

CREATE TABLE medinfo (
    id          integer PRIMARY KEY NOT NULL,
    firstname   text NOT NULL,
    lastname    text NOT NULL,
    city        text NOT NULL,
    illness     text NOT NULL
);

INSERT INTO medinfo VALUES (1, 'Ivo', 'Ivic', 'Zagreb', 'Flu');
INSERT INTO medinfo VALUES (2, 'Ana', 'Anic', 'Split', 'Hypertension');
INSERT INTO medinfo VALUES (3, 'Ivo', 'Ivic', 'Rijeka', 'Asthma');
INSERT INTO medinfo VALUES (4, 'Ana', 'Anic', 'Osijek', 'Migraine');
INSERT INTO medinfo VALUES (5, 'Pero', 'Peric', 'Pula', 'Flu');
