package org.exercise.travellers.parser;

import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDocumentDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.entities.TravellerDocument;


public class TravellerParser extends CommonParser {
    private TravellerParser() {}

    public static TravellerDto toDto(Traveller entity) {
        return new TravellerDto(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getBirthDate(), entity.getEmail(), entity.getMobileNumber(), getTravellerDocument(entity.getActiveDocument()));
    }

    private static TravellerDocumentDto getTravellerDocument(TravellerDocument travellerDocument) {
        return TravellerDocumentParser.toDto(travellerDocument);
    }

    public static Traveller toEntity(CreateTravellerDto dto) {
        Traveller entity = new Traveller();
        entity.setFirstName(sqlInjectionPrevention(dto.getFirstName()));
        entity.setLastName(sqlInjectionPrevention(dto.getLastName()));
        entity.setBirthDate(dto.getBirthDate());
        entity.setEmail(sqlInjectionPrevention(dto.getEmail()));
        entity.setMobileNumber(dto.getMobileNumber());
        entity.setActive(true);
        return entity;
    }

}
