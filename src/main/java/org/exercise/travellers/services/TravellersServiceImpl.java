package org.exercise.travellers.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exercise.travellers.dto.TravellerDocumentDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.exercise.travellers.exception.TravellerNotFoundException;
import org.exercise.travellers.repository.TravellerRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravellersServiceImpl implements TravellersService {

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
    public TravellerDto addTraveller(TravellerDto travellerDto) {
        log.info("Service Implementation ADD");
        return mockTraveller();
    }

    @Override
    public TravellerDto updateTraveller(TravellerDto travellerDto) {
        log.info("Service Implementation UPDATE");
        return mockTraveller();
    }

    @Override
    public void deleteTraveller(String id) {
        log.info("Service Implementation DELETE");
    }


    private TravellerDto mockTraveller() {
        return new TravellerDto("bruno", "jacob", new Date(System.currentTimeMillis()), "ewail@portugal.pt", 931234567, new TravellerDocumentDto(DocumentTypeEnum.ID_CARD, "123", "Portugal"));
    }

}
