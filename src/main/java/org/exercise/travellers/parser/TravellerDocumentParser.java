package org.exercise.travellers.parser;

import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDocumentDto;
import org.exercise.travellers.entities.TravellerDocument;

public class TravellerDocumentParser {

    private TravellerDocumentParser() {
    }

    public static TravellerDocumentDto toDto(TravellerDocument entity) {
        return new TravellerDocumentDto(entity.getDocumentType(), entity.getDocumentNumber(), entity.getIssuingCountry());
    }

    public static TravellerDocument toEntity(CreateTravellerDto dto) {
        TravellerDocument entity = new TravellerDocument();
        entity.setDocumentType(dto.getDocumentTypeEnum());
        entity.setDocumentNumber(dto.getDocumentNumber());
        entity.setIssuingCountry(dto.getIssuingCountry());
        entity.setActive(true);
        return entity;
    }

    public static TravellerDocument toEntity(TravellerDocumentDto dto) {
        TravellerDocument entity = new TravellerDocument();
        entity.setDocumentType(dto.documentTypeEnum());
        entity.setDocumentNumber(dto.documentNumber());
        entity.setIssuingCountry(dto.issuingCountry());
        entity.setActive(true);
        return entity;
    }

}
