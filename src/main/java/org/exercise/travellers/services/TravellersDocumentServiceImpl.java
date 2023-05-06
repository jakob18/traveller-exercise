package org.exercise.travellers.services;

import lombok.RequiredArgsConstructor;
import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.entities.TravellerDocument;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.exercise.travellers.exception.DuplicatedResourcesException;
import org.exercise.travellers.parser.TravellerDocumentParser;
import org.exercise.travellers.repository.TravellerDocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TravellersDocumentServiceImpl implements TravellersDocumentService {

    private final TravellerDocumentRepository travellerDocumentRepository;

    @Override
    public void checkExistingDocument(DocumentTypeEnum documentTypeEnum, String documentNumber, String issuingCountry) {
        if (travellerDocumentRepository.existsByDocumentTypeAndDocumentNumberAndIssuingCountryAndIsActiveTrue(documentTypeEnum, documentNumber, issuingCountry)) {
            throw new DuplicatedResourcesException("The Traveller Document: " + documentNumber + " - " + documentTypeEnum + " - " + issuingCountry + " already exists");
        }
    }

    @Override
    public void createTravelerDocument(Traveller savedTraveller, CreateTravellerDto createTravellerDto) {
        TravellerDocument newTravellerDocument = TravellerDocumentParser.toEntity(createTravellerDto);
        newTravellerDocument.setTraveller(savedTraveller);
        travellerDocumentRepository.save(newTravellerDocument);
    }

    @Override
    public void updateTravellerDocument(Traveller updatedTraveller, TravellerDto newTravellerData) {
        TravellerDocument activeEntity = updatedTraveller.getActiveDocument();
        if (isDifferentDocumentData(activeEntity, newTravellerData)) {
            disableAllDocuments(updatedTraveller);
            createTravelerDocument(updatedTraveller, newTravellerData);
        }
    }

    @Override
    public void disableAllDocuments(Traveller updatedTraveller) {
        List<Long> activeDocumentIds = updatedTraveller.getTravellerDocuments().stream().filter(TravellerDocument::isActive).map(TravellerDocument::getId).toList();
        List<TravellerDocument> documentList = travellerDocumentRepository.findAllById(activeDocumentIds);
        if (!documentList.isEmpty()) {
            documentList.forEach(doc -> doc.setActive(false));
            travellerDocumentRepository.saveAll(documentList);
        }
    }

    private boolean isDifferentDocumentData(TravellerDocument entity, TravellerDto newTravellerData) {
        return entity.getDocumentType() != newTravellerData.travellerDocumentDto().documentTypeEnum() ||
                !entity.getDocumentNumber().equalsIgnoreCase(newTravellerData.travellerDocumentDto().documentNumber()) ||
                !entity.getIssuingCountry().equalsIgnoreCase(newTravellerData.travellerDocumentDto().issuingCountry());
    }

    private void createTravelerDocument(Traveller savedTraveller, TravellerDto travellerDto) {
        TravellerDocument newTravellerDocument = TravellerDocumentParser.toEntity(travellerDto.travellerDocumentDto());
        newTravellerDocument.setTraveller(savedTraveller);
        travellerDocumentRepository.save(newTravellerDocument);
    }

}
