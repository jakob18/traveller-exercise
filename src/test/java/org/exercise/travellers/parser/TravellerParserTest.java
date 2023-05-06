package org.exercise.travellers.parser;

import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.entities.TravellerDocument;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TravellerParserTest {

    @Test
    void toDto() {
        TravellerDto underTest = TravellerParser.toDto(getTraveller());

        assertEquals(1L, underTest.id());
        assertEquals("Bruno", underTest.firstName());
        assertEquals("Jacob", underTest.lastName());
        assertEquals("bruno.jacob@portugal.pt", underTest.email());
        assertEquals(931444555, underTest.mobileNumber());
        assertEquals(DocumentTypeEnum.PASSPORT, underTest.travellerDocumentDto().documentTypeEnum());
        assertEquals("12345", underTest.travellerDocumentDto().documentNumber());
        assertEquals("PORTUGAL", underTest.travellerDocumentDto().issuingCountry());
    }

    @Test
    void toEntity() {
        Traveller underTest = TravellerParser.toEntity(getCreateTravellerDto());

        assertEquals("bruno", underTest.getFirstName());
        assertEquals("jacob", underTest.getLastName());
        assertEquals("bruno.jacob@portugal.pt", underTest.getEmail());
        assertEquals(931234567, underTest.getMobileNumber());
        assertTrue(underTest.isActive());
    }

    private Traveller getTraveller() {
        TravellerDocument entityDocument = new TravellerDocument();
        entityDocument.setDocumentType(DocumentTypeEnum.PASSPORT);
        entityDocument.setDocumentNumber("12345");
        entityDocument.setIssuingCountry("PORTUGAL");
        entityDocument.setActive(true);

        Traveller entity = new Traveller();
        entity.setId(1L);
        entity.setFirstName("Bruno");
        entity.setLastName("Jacob");
        entity.setBirthDate(new Date(1982, 01, 19));
        entity.setEmail("bruno.jacob@portugal.pt");
        entity.setMobileNumber(931444555);
        entity.setActive(true);
        entity.setTravellerDocuments(List.of(entityDocument));
        return entity;
    }

    private CreateTravellerDto getCreateTravellerDto() {
        return new CreateTravellerDto("bruno", "jacob", new Date(1982, 01, 19), "bruno.jacob@portugal.pt", 931234567, DocumentTypeEnum.ID_CARD, "123456", "Portugal");
    }

}
