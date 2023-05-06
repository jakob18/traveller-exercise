package org.exercise.travellers.parser;

import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDocumentDto;
import org.exercise.travellers.entities.TravellerDocument;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TravellerDocumentParserTest {

    @Test
    void toDto() {
        TravellerDocumentDto underTest = TravellerDocumentParser.toDto(getEntity());

        assertEquals(DocumentTypeEnum.PASSPORT, underTest.documentTypeEnum());
        assertEquals("12345", underTest.documentNumber());
        assertEquals("PORTUGAL", underTest.issuingCountry());
    }

    @Test
    void toEntityFromCreateTravellerDto() {
        TravellerDocument underTest = TravellerDocumentParser.toEntity(getCreateTravellerDto());

        assertEquals(DocumentTypeEnum.ID_CARD, underTest.getDocumentType());
        assertEquals("123456", underTest.getDocumentNumber());
        assertEquals("Portugal", underTest.getIssuingCountry());
        assertTrue(underTest.isActive());
    }

    @Test
    void toEntityFromTravellerDocumentDto() {
        TravellerDocument underTest = TravellerDocumentParser.toEntity(getTravellerDocumentDto());

        assertEquals(DocumentTypeEnum.DRIVING_LICENSE, underTest.getDocumentType());
        assertEquals("66666", underTest.getDocumentNumber());
        assertEquals("SPAIN", underTest.getIssuingCountry());
        assertTrue(underTest.isActive());
    }

    private TravellerDocument getEntity() {
        TravellerDocument entity = new TravellerDocument();
        entity.setDocumentType(DocumentTypeEnum.PASSPORT);
        entity.setDocumentNumber("12345");
        entity.setIssuingCountry("PORTUGAL");
        entity.setActive(true);
        return entity;
    }

    private CreateTravellerDto getCreateTravellerDto() {
        return new CreateTravellerDto("bruno", "jacob", new Date(1982, 01, 19), "email@portugal.pt", 931234567, DocumentTypeEnum.ID_CARD, "123456", "Portugal");
    }

    private TravellerDocumentDto getTravellerDocumentDto() {
        return new TravellerDocumentDto(DocumentTypeEnum.DRIVING_LICENSE, "66666", "SPAIN");
    }

}
