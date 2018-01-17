INSERT INTO Role (id, name) VALUES(1, "Adminrolle");
INSERT INTO Role_rights (Role_id, rights) VALUES(1, "ADMIN");
INSERT INTO Profile (id, role_id, username, hashAlgorithm, passwordHash, salt) VALUES (2, 1, "admin", "SHA512", "JeuPx1++vnOSp/rdDwjnPwNIldN49UhtJEwUcKr3ksTvu/3s6nmnWbh+mkjfsw41w1LE8VJJAukn+LpfnUybzg==", "wwUVuetAfN1dtaXk0zWOMmb1nIw=");
INSERT INTO User (id, profile_id, firstname, lastname) VALUES (3, 2, "Chuck", "Norris");

UPDATE hibernate_sequence SET next_val = 4;

INSERT INTO Trade (id, title , description, creator_id) VALUES (1, "My First Trade", "This is my long text for my first trade lamo", 3);
INSERT INTO Trade (id, title , description, creator_id) VALUES (2, "Even more test Trades", "This is my long text for my first trade lamo", 3);

UPDATE hibernate_sequence SET next_val = 3;
