DROP TABLE IF EXISTS medinfo;

CREATE TABLE medinfo (
    multiid integer PRIMARY KEY NOT NULL,
    city    text NOT NULL
);

INSERT INTO medinfo VALUES (1001, 'Zagreb-2-pg');
INSERT INTO medinfo VALUES (1002, 'Split-2-pg');
INSERT INTO medinfo VALUES (1003, 'Rijeka-2-pg');
INSERT INTO medinfo VALUES (1004, 'Osijek-2-pg');
INSERT INTO medinfo VALUES (1005, 'Pula-2-pg');
