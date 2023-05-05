package org.exercise.travellers.parser;

import org.exercise.travellers.dto.TravellerDocumentDto;
import org.exercise.travellers.entities.TravellerDocument;

public class TravellerDocumentParser {

    private TravellerDocumentParser() {
    }

    public static TravellerDocumentDto toDto(TravellerDocument entity) {
        return new TravellerDocumentDto(entity.getDocumentType(), entity.getDocumentNumber(), entity.getIssuingCountry());
    }

}
