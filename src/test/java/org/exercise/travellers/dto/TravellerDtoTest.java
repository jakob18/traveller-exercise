package org.exercise.travellers.dto;

import org.exercise.travellers.enums.DocumentTypeEnum;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class TravellerDtoTest {

    @Test
    void testTravellerDto() {
        TravellerDto underTest = new TravellerDto(1L, "Bruno", "Jacob", new Date(), "bruno.jacob@portugal.pt", 931223444, new TravellerDocumentDto(DocumentTypeEnum.PASSPORT, "123456", "PORTUGAL"));

        assertThat(underTest.id()).isEqualTo(1L);
        assertThat(underTest.firstName()).isEqualTo("Bruno");
        assertThat(underTest.lastName()).isEqualTo("Jacob");
        assertThat(underTest.birthDate()).isInstanceOf(Date.class);
        assertThat(underTest.email()).isEqualTo("bruno.jacob@portugal.pt");
        assertThat(underTest.mobileNumber()).isEqualTo(931223444);
        assertThat(underTest.travellerDocumentDto().documentTypeEnum()).isEqualTo(DocumentTypeEnum.PASSPORT);
        assertThat(underTest.travellerDocumentDto().documentNumber()).isEqualTo("123456");
        assertThat(underTest.travellerDocumentDto().issuingCountry()).isEqualTo("PORTUGAL");
    }

}
