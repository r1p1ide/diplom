CREATE TABLE auth_info
(
    id integer GENERATED ALWAYS AS IDENTITY,
    login varchar(20) NOT NULL,
    password varchar(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_info
(
    id integer GENERATED ALWAYS AS IDENTITY,
    auth_id integer REFERENCES auth_info(id),
    login varchar(20) NOT NULL,
    first_name varchar(100) NOT NULL,
    last_name varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    phone varchar(20) NOT NULL,
    role varchar(10) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE token
(
    id integer GENERATED ALWAYS AS IDENTITY,
    auth_id integer REFERENCES auth_info(id),
    token varchar(50),
    PRIMARY KEY (id)
);