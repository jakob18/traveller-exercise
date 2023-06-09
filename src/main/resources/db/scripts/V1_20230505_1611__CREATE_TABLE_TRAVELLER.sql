CREATE TABLE traveller
(
    ID            BIGINT      NOT NULL AUTO_INCREMENT,
    FIRST_NAME    VARCHAR(50) NOT NULL,
    LAST_NAME     VARCHAR(50) NOT NULL,
    DATE_OF_BIRTH DATE        NOT NULL,
    EMAIL         VARCHAR(50) NOT NULL UNIQUE,
    MOBILE_NUMBER VARCHAR(15) NOT NULL UNIQUE,
    IS_ACTIVE     BOOLEAN     NOT NULL DEFAULT false,
    PRIMARY KEY (ID)
);
