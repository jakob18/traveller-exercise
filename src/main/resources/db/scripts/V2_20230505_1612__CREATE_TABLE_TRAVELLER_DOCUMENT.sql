CREATE TABLE TRAVELLER_DOCUMENT
(
    id              BIGINT                                          NOT NULL AUTO_INCREMENT,
    document_type   ENUM ('passport', 'id_card', 'driving_license') NOT NULL,
    document_number VARCHAR(50)                                     NOT NULL,
    issuing_country VARCHAR(50)                                     NOT NULL,
    is_active       BOOLEAN                                         NOT NULL DEFAULT false,
    traveller_id    BIGINT                                          NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_document (document_type, document_number, issuing_country),
    UNIQUE KEY unique_active_document (traveller_id, is_active),
    FOREIGN KEY (traveller_id) REFERENCES TRAVELLER (id) ON DELETE CASCADE
);
