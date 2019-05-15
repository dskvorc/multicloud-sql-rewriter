DROP TABLE IF EXISTS medinfo;

CREATE TABLE medinfo (
    id          integer PRIMARY KEY NOT NULL,
    firstname   text NOT NULL,
    lastname    text NOT NULL,
    age         integer NOT NULL,
    illness     text NOT NULL
);

--INSERT INTO medinfo VALUES (1, 'Ivo', 'Ivic', 35, 'Flu');
--INSERT INTO medinfo VALUES (2, 'Ana', 'Anic', 48, 'Hypertension');
--INSERT INTO medinfo VALUES (3, 'Ivo', 'Ivic', 70, 'Asthma');
--INSERT INTO medinfo VALUES (4, 'Ana', 'Anic', 56, 'Migraine');
INSERT INTO medinfo VALUES (5, 'Pero', 'Peric', 21, 'Flu');
