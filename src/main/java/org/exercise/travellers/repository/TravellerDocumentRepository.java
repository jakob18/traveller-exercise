package org.exercise.travellers.repository;

import org.exercise.travellers.entities.TravellerDocument;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravellerDocumentRepository extends JpaRepository<TravellerDocument, Long> {

    boolean existsByDocumentTypeAndDocumentNumberAndIssuingCountryAndIsActiveTrue(DocumentTypeEnum documentType, String documentNumber, String issuingCountry);

}
