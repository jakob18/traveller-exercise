package org.exercise.travellers.repository;

import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TravellerRepository extends JpaRepository<Traveller, Long> {

    Optional<Traveller> findByEmailAndIsActiveTrue(String email);

    Optional<Traveller> findByMobileNumberAndIsActiveTrue(String mobile);

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


    boolean existsByEmailAndIsActiveTrue(String email);

    boolean existsByMobileNumberAndIsActiveTrue(int mobile);


}
