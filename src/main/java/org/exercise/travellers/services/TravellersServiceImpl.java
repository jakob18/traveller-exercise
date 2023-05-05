package org.exercise.travellers.services;

import lombok.extern.slf4j.Slf4j;
import org.exercise.travellers.dto.TravellerDocumentDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class TravellersServiceImpl implements TravellersService {

    @Override
    public TravellerDto getTraveller(String searchValue) {
        log.info("Service Implementation GET");
        return mockTraveller();
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
        return new TravellerDto("bruno", "jacob", new Date(System.currentTimeMillis()), "ewail@portugal.pt", 931234567, new TravellerDocumentDto(DocumentTypeEnum.ID_CARD, 123, "Portugal"));
    }

}
