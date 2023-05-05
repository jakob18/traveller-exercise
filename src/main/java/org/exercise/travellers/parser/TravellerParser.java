package org.exercise.travellers.parser;

import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDocumentDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.entities.TravellerDocument;


public class TravellerParser {

    private TravellerParser() {
    }

    public static TravellerDto toDto(Traveller entity) {
        return new TravellerDto(entity.getFirstName(), entity.getLastName(), entity.getBirthDate(), entity.getEmail(), entity.getMobileNumber(), getTravellerDocument(entity.getActiveDocument()));
    }

    private static TravellerDocumentDto getTravellerDocument(TravellerDocument travellerDocument) {
        return TravellerDocumentParser.toDto(travellerDocument);
    }

    public static Traveller toEntity(CreateTravellerDto dto) {
        Traveller entity = new Traveller();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastNAme());
        entity.setBirthDate(dto.getBirthDate());
        entity.setEmail(dto.getEmail());
        entity.setMobileNumber(dto.getMobileNumber());
        entity.setActive(true);
        return entity;
    }

}