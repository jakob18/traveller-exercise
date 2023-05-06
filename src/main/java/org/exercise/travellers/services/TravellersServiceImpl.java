package org.exercise.travellers.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.exercise.travellers.exception.DuplicatedResourcesException;
import org.exercise.travellers.exception.TravellerDeactivatedException;
import org.exercise.travellers.exception.TravellerNotFoundException;
import org.exercise.travellers.parser.TravellerParser;
import org.exercise.travellers.repository.TravellerRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravellersServiceImpl implements TravellersService {

    private final TravellersDocumentService travellersDocumentService;
    private final TravellerRepository travellerRepository;


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
        checkExistingEmail(createTravellerDto.getEmail());
        checkExistingMobileNumber(createTravellerDto.getMobileNumber());
        travellersDocumentService.checkExistingDocument(createTravellerDto.getDocumentTypeEnum(), createTravellerDto.getDocumentNumber(), createTravellerDto.getIssuingCountry());
        Traveller traveller = createTraveller(createTravellerDto);
        return travellerRepository.findById(traveller.getId()).orElseThrow(() -> new TravellerNotFoundException("Couldn't find the new traveller with the Id: " + traveller.getId()));
    }

    private void checkExistingEmail(String email) {
        if (travellerRepository.existsByEmailAndIsActiveTrue(email)) {
            throw new DuplicatedResourcesException("The email: " + email + " already exists");
        }
    }

    private void checkExistingMobileNumber(int mobileNumber) {
        if (travellerRepository.existsByMobileNumberAndIsActiveTrue(mobileNumber)) {
            throw new DuplicatedResourcesException("The Mobile Number: " + mobileNumber + " already exists");
        }
    }

    private Traveller createTraveller(CreateTravellerDto createTravellerDto) {
        Traveller savedTraveller = travellerRepository.save(TravellerParser.toEntity(createTravellerDto));
        travellersDocumentService.createTravelerDocument(savedTraveller, createTravellerDto);
        return savedTraveller;
    }

    @Override
    public Traveller updateTraveller(TravellerDto travellerDto) {
        Optional<Traveller> entity = travellerRepository.findById(travellerDto.id());
        Traveller editTraveller = checkUpdateTraveller(entity, travellerDto.id());
        travellersDocumentService.updateTravellerDocument(editTraveller, travellerDto);
        Traveller updatedTraveller = updateTraveller(editTraveller, travellerDto);
        return travellerRepository.findById(updatedTraveller.getId()).orElseThrow(() -> new TravellerNotFoundException("Couldn't find the traveller with the Id: " + updatedTraveller.getId()));
    }

    private Traveller checkUpdateTraveller(Optional<Traveller> traveller, Long id) {
        if (traveller.isEmpty()) {
            throw new TravellerNotFoundException("The traveller with the Id: " + id + " was not found");
        }

        Traveller checkTraveller = traveller.get();
        if (!checkTraveller.isActive()) {
            throw new TravellerDeactivatedException("The traveller with the Id: " + id + " is not active");
        }

        return checkTraveller;
    }

    private Traveller updateTraveller(Traveller oldTravellerData, TravellerDto newTravellerData) {
        try {
            oldTravellerData.setFirstName(newTravellerData.firstName());
            oldTravellerData.setLastName(newTravellerData.lastName());
            oldTravellerData.setBirthDate(newTravellerData.birthDate());
            oldTravellerData.setEmail(newTravellerData.email());
            oldTravellerData.setMobileNumber(newTravellerData.mobileNumber());
            return travellerRepository.save(oldTravellerData);
        } catch (Exception ex) {
            throw new DuplicatedResourcesException(ex.getLocalizedMessage());
        }
    }

    @Override
    public void deleteTraveller(Long id) {
        Traveller deleteTraveller = travellerRepository.findByIdAndIsActiveTrue(id).orElseThrow(() -> new TravellerNotFoundException("Couldn't find the traveller with the Id: " + id + " or is already deleted"));
        travellersDocumentService.disableAllDocuments(deleteTraveller);
        deleteTraveller.setActive(false);
        travellerRepository.save(deleteTraveller);
    }
}
