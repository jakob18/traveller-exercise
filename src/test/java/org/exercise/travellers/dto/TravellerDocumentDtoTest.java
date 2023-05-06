package org.exercise.travellers.dto;

import org.exercise.travellers.enums.DocumentTypeEnum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class TravellerDocumentDtoTest {

    @Test
    void testTravellerDto() {
        TravellerDocumentDto underTest = new TravellerDocumentDto(DocumentTypeEnum.PASSPORT, "123456", "PORTUGAL");

        assertThat(underTest.documentTypeEnum()).isEqualTo(DocumentTypeEnum.PASSPORT);
        assertThat(underTest.documentNumber()).isEqualTo("123456");
        assertThat(underTest.issuingCountry()).isEqualTo("PORTUGAL");
    }

}
