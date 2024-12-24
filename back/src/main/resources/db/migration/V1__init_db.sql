CREATE TABLE users
(
    id       INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    username VARCHAR(255)                             NOT NULL,
    password VARCHAR(255)                             NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

CREATE TABLE employee
(
    id            INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    full_name     VARCHAR(255)                             NOT NULL,
    date_of_birth date                                     NOT NULL,
    CONSTRAINT pk_employee PRIMARY KEY (id)
);

ALTER TABLE employee
    ADD CONSTRAINT uc_employee_fullname UNIQUE (full_name);


INSERT INTO employee(date_of_birth, full_name)
VALUES ('2000-04-20', 'Sarah Miller');

INSERT INTO employee(date_of_birth, full_name)
VALUES ('1995-04-09', 'Rodolfo Alabar');

INSERT INTO employee(date_of_birth, full_name)
VALUES ('1999-12-01', 'Katerina Valeria');