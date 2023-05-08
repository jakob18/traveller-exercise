package org.exercise.travellers.repository;

import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TravellerRepository extends JpaRepository<Traveller, Long> {

    Optional<Traveller> findByEmailAndIsActiveTrue(String email);

    Optional<Traveller> findByMobileNumberAndIsActiveTrue(int mobile);

    @Query("""
        SELECT t 
        FROM Traveller t 
        INNER JOIN t.travellerDocuments td 
        WHERE td.documentNumber = :documentNumber 
        AND td.documentType = :documentType 
        AND td.isActive = true 
        AND t.isActive = true
        """)
    Optional<Traveller> findByDocument(String documentNumber, DocumentTypeEnum documentType);

    @Query("""
        SELECT t 
        FROM Traveller t 
        INNER JOIN t.travellerDocuments td 
        WHERE td.documentNumber = :documentNumber 
        AND td.documentType = :documentType 
        AND td.issuingCountry = :issuingCountry
        AND td.isActive = true 
        AND t.isActive = true
        """)
    Optional<Traveller> findByDocument(String documentNumber, DocumentTypeEnum documentType, String issuingCountry);


    boolean existsByEmail(String email);

    boolean existsByMobileNumber(int mobile);

    Optional<Traveller> findByIdAndIsActiveTrue(long id);

}
