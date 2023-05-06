package org.exercise.travellers.dto;

import org.exercise.travellers.enums.DocumentTypeEnum;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateTravellerDtoTest {

    @Test
    void createTravellerDto() {
        String firstName = "Bruno";
        String lastName = "Jacob";
        Date birthDate = new Date();
        String email = "bruno.jacob@portugal.pt";
        int mobileNumber = 931223444;
        DocumentTypeEnum documentTypeEnum = DocumentTypeEnum.PASSPORT;
        String documentNumber = "5678";
        String issuingCountry = "PORTUGAL";

        CreateTravellerDto underTest = new CreateTravellerDto(firstName, lastName, birthDate, email, mobileNumber, documentTypeEnum, documentNumber, issuingCountry);

        assertEquals(firstName, underTest.getFirstName());
        assertEquals(lastName, underTest.getLastNAme());
        assertEquals(birthDate, underTest.getBirthDate());
        assertEquals(email, underTest.getEmail());
        assertEquals(mobileNumber, underTest.getMobileNumber());
        assertEquals(documentTypeEnum, underTest.getDocumentTypeEnum());
        assertEquals(documentNumber, underTest.getDocumentNumber());
        assertEquals(issuingCountry, underTest.getIssuingCountry());
    }

    @Test
    void createTravellerDtoWithNullValues() {
        assertThrows(NullPointerException.class, () -> new CreateTravellerDto(null, "Jacob", new Date(), "bruno.jacob@portugal.pt", 931223444, DocumentTypeEnum.PASSPORT, "5678", "PORTUGAL"));
        assertThrows(NullPointerException.class, () -> new CreateTravellerDto("Bruno", null, new Date(), "bruno.jacob@portugal.pt", 931223444, DocumentTypeEnum.PASSPORT, "5678", "PORTUGAL"));
        assertThrows(NullPointerException.class, () -> new CreateTravellerDto("Bruno", "Jacob", null, "bruno.jacob@portugal.pt", 931223444, DocumentTypeEnum.PASSPORT, "5678", "PORTUGAL"));
        assertThrows(NullPointerException.class, () -> new CreateTravellerDto("Bruno", "Jacob", new Date(), null, 931223444, DocumentTypeEnum.PASSPORT, "5678", "PORTUGAL"));
        assertThrows(NullPointerException.class, () -> new CreateTravellerDto("Bruno", "Jacob", new Date(), "bruno.jacob@portugal.pt", 931223444, null, "5678", "PORTUGAL"));
        assertThrows(NullPointerException.class, () -> new CreateTravellerDto("Bruno", "Jacob", new Date(), "bruno.jacob@portugal.pt", 931223444, DocumentTypeEnum.PASSPORT, null, "PORTUGAL"));
        assertThrows(NullPointerException.class, () -> new CreateTravellerDto("Bruno", "Jacob", new Date(), "bruno.jacob@portugal.pt", 931223444, DocumentTypeEnum.PASSPORT, "5678", null));
    }
}







