package org.exercise.travellers.dto;

import org.exercise.travellers.enums.DocumentTypeEnum;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(lastName, underTest.getLastName());
        assertEquals(birthDate, underTest.getBirthDate());
        assertEquals(email, underTest.getEmail());
        assertEquals(mobileNumber, underTest.getMobileNumber());
        assertEquals(documentTypeEnum, underTest.getDocumentTypeEnum());
        assertEquals(documentNumber, underTest.getDocumentNumber());
        assertEquals(issuingCountry, underTest.getIssuingCountry());
    }
}







