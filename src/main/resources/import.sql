/* Populate tabla regiones */
INSERT INTO regiones (id, name) VALUES(1, "Sudamérica");
INSERT INTO regiones (id, name) VALUES(2, "Centroamérica");
INSERT INTO regiones (id, name) VALUES(3, "Norteamérica");
INSERT INTO regiones (id, name) VALUES(4, "Europa");
INSERT INTO regiones (id, name) VALUES(5, "Asia");
INSERT INTO regiones (id, name) VALUES(6, "Africa");
INSERT INTO regiones (id, name) VALUES(7, "Oceanía");


/* Populate tabla clientes */
INSERT INTO clientes (region_id, name, last_name, email, created_at) VALUES(1, 'Andrés', 'Guzmán', 'profesor@bolsadeideas.com', '2018-01-01');
INSERT INTO clientes (region_id, name, last_name, email, created_at) VALUES(2, 'Mr. John', 'Doe', 'john.doe@gmail.com', '2018-01-02');
INSERT INTO clientes (region_id, name, last_name, email, created_at) VALUES(4, 'Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2018-01-03');
INSERT INTO clientes (region_id, name, last_name, email, created_at) VALUES(4, 'Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2018-01-04');
INSERT INTO clientes (region_id, name, last_name, email, created_at) VALUES(4, 'Erich', 'Gamma', 'erich.gamma@gmail.com', '2018-02-01');
INSERT INTO clientes (region_id, name, last_name, email, created_at) VALUES(3, 'Richard', 'Helm', 'richard.helm@gmail.com', '2018-02-10');
INSERT INTO clientes (region_id, name, last_name, email, created_at) VALUES(3, 'Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2018-02-18');
INSERT INTO clientes (region_id, name, last_name, email, created_at) VALUES(3, 'John', 'Vlissides', 'john.vlissides@gmail.com', '2018-02-28');
INSERT INTO clientes (region_id, name, last_name, email, created_at) VALUES(3, 'Dr. James', 'Gosling', 'james.gosling@gmail.com', '2018-03-03');
INSERT INTO clientes (region_id, name, last_name, email, created_at) VALUES(5, 'Magma', 'Lee', 'magma.lee@gmail.com', '2018-03-04');
INSERT INTO clientes (region_id, name, last_name, email, created_at) VALUES(6, 'Tornado', 'Roe', 'tornado.roe@gmail.com', '2018-03-05');
INSERT INTO clientes (region_id, name, last_name, email, created_at) VALUES(7, 'Jade', 'Doe', 'jane.doe@gmail.com', '2018-03-06');


/* tabla de usuarios */
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES('javier','$2a$10$GhD36xoL8usybbXf9VdAouramjmr3cwTzDAwh4qh4SxG4bU/qK4de',1, 'Javier', 'Paredes', 'javiparedes160@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES('admin','$2a$10$bUZpLO2al2oW7u7YcGN2I.DNzr.QSIWuWd5B30WniNGFtnhcIeRa.',1, 'Andres', 'Gonzalez', 'agonzalez@gmail.com');

INSERT INTO roles (nombre) VALUES('ROLE_USER');
INSERT INTO roles (nombre) VALUES('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(1,1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(2,2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(2,1);

