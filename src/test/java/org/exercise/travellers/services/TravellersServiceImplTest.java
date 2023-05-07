package org.exercise.travellers.services;

import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDocumentDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.entities.TravellerDocument;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.exercise.travellers.exception.DuplicatedResourcesException;
import org.exercise.travellers.exception.TravellerDeactivatedException;
import org.exercise.travellers.exception.TravellerNotFoundException;
import org.exercise.travellers.parser.TravellerParser;
import org.exercise.travellers.repository.TravellerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class})
class TravellersServiceImplTest {

    @Mock
    private TravellerRepository travellerRepository;

    @Mock
    private TravellersDocumentService travellersDocumentService;

    @InjectMocks
    private TravellersServiceImpl underTest;

    @Test
    void getTravellerByEmail() {
        String searchValue = "bruno.jacob@portugal.pt";

        when(travellerRepository.findByEmailAndIsActiveTrue(anyString())).thenReturn(Optional.of(getTraveller()));

        Traveller result = underTest.getTraveller(searchValue);

        verify(travellerRepository).findByEmailAndIsActiveTrue(searchValue);
        assertThat(result.getFirstName()).isEqualTo(getTraveller().getFirstName());
        assertThat(result.getLastName()).isEqualTo(getTraveller().getLastName());
        assertThat(result.getEmail()).isEqualTo(getTraveller().getEmail());
        assertTrue(result.isActive());
    }

    @Test
    void getTravellerByMobile() {
        String searchValue = "931345666";

        when(travellerRepository.findByMobileNumberAndIsActiveTrue(anyString())).thenReturn(Optional.of(getTraveller()));

        Traveller result = underTest.getTraveller(searchValue);

        verify(travellerRepository).findByMobileNumberAndIsActiveTrue(searchValue);
        assertThat(result.getFirstName()).isEqualTo(getTraveller().getFirstName());
        assertThat(result.getLastName()).isEqualTo(getTraveller().getLastName());
        assertThat(result.getEmail()).isEqualTo(getTraveller().getEmail());
        assertTrue(result.isActive());
    }

    @Test
    void getTravellerByDocument() {
        String searchValue = "PASSPORT1234";

        when(travellerRepository.findByDocument(anyString(), any())).thenReturn(Optional.of(getTraveller()));

        Traveller result = underTest.getTraveller(searchValue);

        verify(travellerRepository).findByDocument("1234", DocumentTypeEnum.PASSPORT);
        assertThat(result.getFirstName()).isEqualTo(getTraveller().getFirstName());
        assertThat(result.getLastName()).isEqualTo(getTraveller().getLastName());
        assertThat(result.getEmail()).isEqualTo(getTraveller().getEmail());
        assertTrue(result.isActive());
    }

    @Test
    void getTravellerNotFound() {
        String searchValue = "bruno.jacob@portugal.pt";

        when(travellerRepository.findByEmailAndIsActiveTrue(anyString())).thenReturn(Optional.empty());

        assertThrows(TravellerNotFoundException.class, () -> underTest.getTraveller(searchValue));
        verify(travellerRepository).findByEmailAndIsActiveTrue(searchValue);
    }

    @Test
    void addTraveller() {
        when(travellerRepository.existsByEmail(anyString())).thenReturn(false);
        when(travellerRepository.existsByMobileNumber(anyInt())).thenReturn(false);
        doNothing().when(travellersDocumentService).checkExistingDocument(any(), anyString(), anyString());
        when(travellerRepository.save(any())).thenReturn(getTraveller());
        doNothing().when(travellersDocumentService).createTravelerDocument(any(), any());
        when(travellerRepository.findById(any())).thenReturn(Optional.of(getTraveller()));

        Traveller result = underTest.addTraveller(getCreateTravellerDto());
        verify(travellerRepository).existsByEmail("bruno.jacob@portugal.pt");
        verify(travellerRepository).existsByMobileNumber(931234567);
        verify(travellerRepository).save(any());
        verify(travellerRepository).findById(1L);
        assertThat(result.getFirstName()).isEqualTo(getTraveller().getFirstName());
        assertThat(result.getLastName()).isEqualTo(getTraveller().getLastName());
        assertThat(result.getEmail()).isEqualTo(getTraveller().getEmail());
        assertTrue(result.isActive());
    }

    @Test
    void addTravellerExistingEmail() {
        when(travellerRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(DuplicatedResourcesException.class, () -> underTest.addTraveller(getCreateTravellerDto()));

        verify(travellerRepository).existsByEmail("bruno.jacob@portugal.pt");
        verify(travellerRepository, times(0)).existsByMobileNumber(anyInt());
        verify(travellerRepository, times(0)).save(any());
        verify(travellerRepository, times(0)).findById(any());
    }

    @Test
    void addTravellerExistingMobile() {
        when(travellerRepository.existsByEmail(anyString())).thenReturn(false);
        when(travellerRepository.existsByMobileNumber(anyInt())).thenReturn(true);

        assertThrows(DuplicatedResourcesException.class, () -> underTest.addTraveller(getCreateTravellerDto()));

        verify(travellerRepository).existsByEmail("bruno.jacob@portugal.pt");
        verify(travellerRepository).existsByMobileNumber(931234567);
        verify(travellerRepository, times(0)).save(any());
        verify(travellerRepository, times(0)).findById(any());
    }

    @Test
    void updateTraveller() {
        Traveller update = getTraveller();
        update.setLastName("Miguel");
        TravellerDto updateDto = new TravellerDto(1L, "Bruno", "Miguel", new Date(1982,Calendar.JANUARY,19), "bruno.jacob@portugal.pt", 931234567, new TravellerDocumentDto(DocumentTypeEnum.ID_CARD, "123456", "Portugal"));

        when(travellerRepository.findById(any())).thenReturn(Optional.of(getTraveller()));
        doNothing().when(travellersDocumentService).updateTravellerDocument(any(), any());
        when(travellerRepository.save(any())).thenReturn(update);

        Traveller result = underTest.updateTraveller(updateDto);

        verify(travellerRepository, times(2)).findById(1L);
        verify(travellerRepository).save(any());
        assertThat(result.getFirstName()).isEqualTo(getTraveller().getFirstName());
        assertThat(result.getLastName()).isEqualTo("Miguel");
        assertThat(result.getEmail()).isEqualTo(getTraveller().getEmail());
        assertTrue(result.isActive());
    }

    @Test
    void updateTravellerNotFound() {
        when(travellerRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(TravellerNotFoundException.class, () -> underTest.updateTraveller(getTravellerDto()));
        verify(travellerRepository).findById(1L);
    }

    @Test
    void updateTravellerIsNotActive() {
        Traveller entity = getTraveller();
        entity.setActive(false);
        when(travellerRepository.findById(any())).thenReturn(Optional.of(entity));

        assertThrows(TravellerDeactivatedException.class, () -> underTest.updateTraveller(getTravellerDto()));
        verify(travellerRepository).findById(1L);
    }

    @Test
    void updateTravellerSaveException() {
        Traveller update = getTraveller();
        update.setLastName("Miguel");
        TravellerDto updateDto = new TravellerDto(1L, "Bruno", "Miguel", new Date(1982,Calendar.JANUARY,19), "bruno.jacob@portugal.pt", 931234567, new TravellerDocumentDto(DocumentTypeEnum.ID_CARD, "123456", "Portugal"));

        when(travellerRepository.findById(any())).thenReturn(Optional.of(getTraveller()));
        doNothing().when(travellersDocumentService).updateTravellerDocument(any(), any());
        when(travellerRepository.save(any())).thenThrow(new DuplicatedResourcesException("Email already in use"));

        assertThrows(Exception.class, () -> underTest.updateTraveller(updateDto));

        verify(travellerRepository).findById(1L);
        verify(travellerRepository).save(any());

    }

    @Test
    void deleteTraveller() {
        when(travellerRepository.findByIdAndIsActiveTrue(anyLong())).thenReturn(Optional.of(getTraveller()));
        doNothing().when(travellersDocumentService).disableAllDocuments(any());
        when(travellerRepository.save(any())).thenReturn(getTraveller());

        underTest.deleteTraveller(1L);

        verify(travellerRepository).findByIdAndIsActiveTrue(1L);
        verify(travellerRepository).save(any());
    }

    @Test
    void deleteTravellerNotFound() {
        when(travellerRepository.findByIdAndIsActiveTrue(anyLong())).thenReturn(Optional.empty());

        assertThrows(TravellerNotFoundException.class, () -> underTest.deleteTraveller(1L));

        verify(travellerRepository).findByIdAndIsActiveTrue(1L);
        verify(travellerRepository, times(0)).save(any());
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
