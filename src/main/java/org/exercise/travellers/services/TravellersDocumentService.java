package org.exercise.travellers.services;

import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.enums.DocumentTypeEnum;

public interface TravellersDocumentService {

    void checkExistingDocument(DocumentTypeEnum documentTypeEnum, String documentNumber, String issuingCountry);

    void createTravelerDocument(Traveller savedTraveller, CreateTravellerDto createTravellerDto);

    void updateTravellerDocument(Traveller updatedTraveller, TravellerDto newTravellerData);

    void disableAllDocuments(Traveller updatedTraveller);
}
