CREATE TABLE TRAVELLER_DOCUMENT
(
    ID              BIGINT                                          NOT NULL AUTO_INCREMENT,
    DOCUMENT_TYPE   ENUM ('PASSPORT', 'ID_CARD', 'DRIVING_LICENSE') NOT NULL,
    DOCUMENT_NUMBER VARCHAR(50)                                     NOT NULL,
    ISSUING_COUNTRY VARCHAR(50)                                     NOT NULL,
    IS_ACTIVE       BOOLEAN                                         NOT NULL DEFAULT false,
    TRAVELLER_ID    BIGINT                                          NOT NULL,
    PRIMARY KEY (ID),
    UNIQUE KEY unique_document (DOCUMENT_TYPE, DOCUMENT_NUMBER, ISSUING_COUNTRY),
    UNIQUE KEY unique_active_document (TRAVELLER_ID, IS_ACTIVE),
    FOREIGN KEY (TRAVELLER_ID) REFERENCES TRAVELLER (ID) ON DELETE CASCADE
);
