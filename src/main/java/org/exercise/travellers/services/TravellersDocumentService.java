package org.exercise.travellers.services;

import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.enums.DocumentTypeEnum;

public interface TravellersDocumentService {

    void checkExistingDocument(DocumentTypeEnum documentTypeEnum, String documentNumber, String issuingCountry);

    void createTravelerDocument(Traveller savedTraveller, CreateTravellerDto createTravellerDto);

    void updateTravellerDocument(Traveller updatedTraveller, TravellerDto newTravellerData);

    /**
     * Because I can't use a UNIQUE KEY between Traveler_Id and Is_Active=true I decided to always try to disable all the active documents og the traveller
     * It should only have one active document but it's better to prevent some manipulation cases
     *
     * @param updatedTraveller the traveller object where all documents are going to be disabled
     */
    void disableAllDocuments(Traveller updatedTraveller);
}
