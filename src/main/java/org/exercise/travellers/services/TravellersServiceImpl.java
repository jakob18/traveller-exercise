package org.exercise.travellers.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.entities.TravellerDocument;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.exercise.travellers.exception.DuplicatedResourcesException;
import org.exercise.travellers.exception.TravellerNotFoundException;
import org.exercise.travellers.parser.TravellerDocumentParser;
import org.exercise.travellers.parser.TravellerParser;
import org.exercise.travellers.repository.TravellerDocumentRepository;
import org.exercise.travellers.repository.TravellerRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravellersServiceImpl implements TravellersService {

    private final TravellerRepository travellerRepository;
    private final TravellerDocumentRepository travellerDocumentRepository;

    @Override
    public Traveller getTraveller(String searchValue) {
        Optional<Traveller> traveller = Optional.empty();

        if (isEmail(searchValue)) {
            traveller = travellerRepository.findByEmailAndIsActiveTrue(searchValue);
        } else if (isMobile(searchValue)) {
            traveller = travellerRepository.findByMobileNumberAndIsActiveTrue(searchValue);
        } else if (isDocument(searchValue)) {
            DocumentTypeEnum documentType = getDocumentType(searchValue);
            String documentNumber = searchValue.substring(documentType.toString().length());
            traveller = travellerRepository.findByDocument(documentNumber, documentType);
        }

        return traveller.orElseThrow(() -> new TravellerNotFoundException("There isn't an active Traveller with the value: " + searchValue));
    }

    private boolean isEmail(String value) {
        return value.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
    }

    private boolean isMobile(String value) {
        return value.matches("\\+?[0-9]{7,15}");
    }

    private boolean isDocument(String value) {
        return Arrays.stream(DocumentTypeEnum.values())
                .anyMatch(documentTypeEnum -> value.startsWith(documentTypeEnum.toString()));
    }

    private DocumentTypeEnum getDocumentType(String value) {
        return Arrays.stream(DocumentTypeEnum.values())
                .filter(documentType -> value.startsWith(documentType.toString()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Traveller addTraveller(CreateTravellerDto createTravellerDto) {
        checkExistingValues(createTravellerDto);
        Traveller traveller = createTraveller(createTravellerDto);
        return travellerRepository.findById(traveller.getId()).orElseThrow(() -> new TravellerNotFoundException("Couldn't find the new traveller with the Id: " + traveller.getId()));
    }

    private void checkExistingValues(CreateTravellerDto createTravellerDto) {
        if (travellerRepository.existsByEmailAndIsActiveTrue(createTravellerDto.getEmail())) {
            throw new DuplicatedResourcesException("The email: " + createTravellerDto.getEmail() + " already exists");
        }

        if (travellerRepository.existsByMobileNumberAndIsActiveTrue(String.valueOf(createTravellerDto.getMobileNumber()))) {
            throw new DuplicatedResourcesException("The Mobile Number: " + createTravellerDto.getMobileNumber() + " already exists");
        }

        if (travellerDocumentRepository.existsByDocumentTypeAndDocumentNumberAndIssuingCountryAndIsActiveTrue(createTravellerDto.getDocumentTypeEnum(), createTravellerDto.getDocumentNumber(), createTravellerDto.getIssuingCountry())) {
            throw new DuplicatedResourcesException("The Traveller Document: " + createTravellerDto.getDocumentNumber() + " - " +
                                                           createTravellerDto.getDocumentTypeEnum() + " - " + createTravellerDto.getIssuingCountry() + " already exists");
        }
    }

    private Traveller createTraveller(CreateTravellerDto createTravellerDto) {
        Traveller savedTraveller = travellerRepository.save(TravellerParser.toEntity(createTravellerDto));
        createTravelerDocument(savedTraveller, createTravellerDto);
        return savedTraveller;
    }

    private void createTravelerDocument(Traveller savedTraveller, CreateTravellerDto createTravellerDto) {
        TravellerDocument newTravellerDocument = TravellerDocumentParser.toEntity(createTravellerDto);
        newTravellerDocument.setTraveller(savedTraveller);
        travellerDocumentRepository.save(newTravellerDocument);
    }

    @Override
    public Traveller updateTraveller(CreateTravellerDto travellerDto) {
        log.info("Service Implementation UPDATE");
        return null;
    }

    @Override
    public void deleteTraveller(String id) {
        log.info("Service Implementation DELETE");
    }

}
