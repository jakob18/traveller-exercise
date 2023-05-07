package org.exercise.travellers.services;

import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.entities.TravellerDocument;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.exercise.travellers.exception.DuplicatedResourcesException;
import org.exercise.travellers.parser.TravellerParser;
import org.exercise.travellers.repository.TravellerDocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
class TravellersDocumentServiceImplTest {

    @Mock
    private TravellerDocumentRepository travellerDocumentRepository;

    @InjectMocks
    private TravellersDocumentServiceImpl underTest;

    @Test
    void checkExistingDocumentFalse() {
        when(travellerDocumentRepository.existsByDocumentTypeAndDocumentNumberAndIssuingCountryAndIsActiveTrue(any(), anyString(), anyString())).thenReturn(false);

        underTest.checkExistingDocument(DocumentTypeEnum.PASSPORT, "1234", "Portugal");

        verify(travellerDocumentRepository).existsByDocumentTypeAndDocumentNumberAndIssuingCountryAndIsActiveTrue(DocumentTypeEnum.PASSPORT, "1234", "Portugal");
    }

    @Test
    void checkExistingDocumentTrue() {
        when(travellerDocumentRepository.existsByDocumentTypeAndDocumentNumberAndIssuingCountryAndIsActiveTrue(any(), anyString(), anyString())).thenReturn(true);

        Throwable thrown = catchThrowable(() -> underTest.checkExistingDocument(DocumentTypeEnum.PASSPORT, "1234", "Portugal"));

        assertThat(thrown)
                .as("The Traveller Document already exists on the Database")
                .isInstanceOf(DuplicatedResourcesException.class)
                .hasMessage("The Traveller Document: 1234 - PASSPORT - Portugal already exists");

        verify(travellerDocumentRepository).existsByDocumentTypeAndDocumentNumberAndIssuingCountryAndIsActiveTrue(DocumentTypeEnum.PASSPORT, "1234", "Portugal");
    }

    @Test
    void createTravelerDocument() {
        when(travellerDocumentRepository.save(any())).thenReturn(getTravellerDocument());
        underTest.createTravelerDocument(getTraveller(), getCreateTravellerDto());

        verify(travellerDocumentRepository).save(any());
    }

    @Test
    void updateTravellerDocumentSameDocument() {
        when(travellerDocumentRepository.findAllById(any())).thenReturn(List.of(getTravellerDocument()));

        underTest.updateTravellerDocument(getTraveller(), getTravellerDto());

        verify(travellerDocumentRepository, times(0)).findAllById(List.of(1L));
        verify(travellerDocumentRepository, times(0)).saveAll(any());
    }

    @Test
    void updateTravellerDocumentDifferentDocument() {
        TravellerDocument travellerDocument = new TravellerDocument();
        travellerDocument.setId(2L);
        travellerDocument.setDocumentType(DocumentTypeEnum.ID_CARD);
        travellerDocument.setDocumentNumber("123");
        travellerDocument.setIssuingCountry("Portugal");
        travellerDocument.setActive(true);
        Traveller traveller = getTraveller();
        traveller.setTravellerDocuments(List.of(travellerDocument));

        when(travellerDocumentRepository.findAllById(any())).thenReturn(List.of(getTravellerDocument()));
        when(travellerDocumentRepository.saveAll(any())).thenReturn(List.of(new TravellerDocument()));

        underTest.updateTravellerDocument(traveller, getTravellerDto());

        verify(travellerDocumentRepository).findAllById(List.of(2L));
        verify(travellerDocumentRepository).saveAll(any());
    }

    @Test
    void disableAllDocuments() {
        when(travellerDocumentRepository.findAllById(any())).thenReturn(List.of(getTravellerDocument()));
        when(travellerDocumentRepository.saveAll(any())).thenReturn(List.of(new TravellerDocument()));

        underTest.disableAllDocuments(getTraveller());

        verify(travellerDocumentRepository).findAllById(List.of(1L));
        verify(travellerDocumentRepository).saveAll(any());
    }

    private Traveller getTraveller() {
        Traveller entity = new Traveller();
        entity.setId(1L);
        entity.setFirstName("Bruno");
        entity.setLastName("Jacob");
        entity.setBirthDate(new Date(1982, Calendar.JANUARY, 19));
        entity.setEmail("bruno.jacob@portugal.pt");
        entity.setMobileNumber(931444555);
        entity.setActive(true);
        entity.setTravellerDocuments(List.of(getTravellerDocument()));
        return entity;
    }

    private TravellerDocument getTravellerDocument() {
        TravellerDocument entityDocument = new TravellerDocument();
        entityDocument.setId(1L);
        entityDocument.setDocumentType(DocumentTypeEnum.PASSPORT);
        entityDocument.setDocumentNumber("12345");
        entityDocument.setIssuingCountry("PORTUGAL");
        entityDocument.setActive(true);
        return entityDocument;
    }

    private CreateTravellerDto getCreateTravellerDto() {
        return new CreateTravellerDto("bruno", "jacob", new Date(1982, Calendar.JANUARY, 19), "bruno.jacob@portugal.pt", 931234567, DocumentTypeEnum.ID_CARD, "123456", "Portugal");
    }

    private TravellerDto getTravellerDto() {
        return TravellerParser.toDto(getTraveller());
    }

}
