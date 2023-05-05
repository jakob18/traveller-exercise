CREATE TABLE TRAVELLER
(
    id            BIGINT      NOT NULL AUTO_INCREMENT,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    date_of_birth DATE        NOT NULL,
    email         VARCHAR(50) NOT NULL UNIQUE,
    mobile_number VARCHAR(15) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
