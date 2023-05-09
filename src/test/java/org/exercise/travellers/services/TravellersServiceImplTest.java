package org.exercise.travellers.services;

import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDocumentDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.entities.TravellerDocument;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.exercise.travellers.exception.DuplicatedResourcesException;
import org.exercise.travellers.exception.InvalidEmailException;
import org.exercise.travellers.exception.TravellerDeactivatedException;
import org.exercise.travellers.exception.TravellerNotFoundException;
import org.exercise.travellers.parser.TravellerDocumentParser;
import org.exercise.travellers.parser.TravellerParser;
import org.exercise.travellers.repository.TravellerRepository;
import org.exercise.travellers.specification.TravellerSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;
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

    @Mock
    private TravellerSpecification travellerSpecification;

    @InjectMocks
    private TravellersServiceImpl underTest;

    @Test
    void getTravellerSearchByEmail() {
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
    void getTravellerSearchByMobile() {
        String searchValue = "931345666";

        when(travellerRepository.findByMobileNumberAndIsActiveTrue(anyInt())).thenReturn(Optional.of(getTraveller()));

        Traveller result = underTest.getTraveller(searchValue);

        verify(travellerRepository).findByMobileNumberAndIsActiveTrue(Integer.parseInt(searchValue));
        assertThat(result.getFirstName()).isEqualTo(getTraveller().getFirstName());
        assertThat(result.getLastName()).isEqualTo(getTraveller().getLastName());
        assertThat(result.getEmail()).isEqualTo(getTraveller().getEmail());
        assertTrue(result.isActive());
    }

    @Test
    void getTravellerSearchByDocument() {
        String searchValue = "PASSPORT1234";

        when(travellerRepository.findOne((Specification<Traveller>) null)).thenReturn(Optional.of(getTraveller()));
        when(travellerSpecification.findOneByDocuments(anyString(), any())).thenReturn(null);

        Traveller result = underTest.getTraveller(searchValue);

        verify(travellerSpecification).findOneByDocuments("1234", DocumentTypeEnum.PASSPORT);
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
    void getTravellerByEmail() {
        String email = "bruno.jacob@portugal.pt";

        when(travellerRepository.findByEmailAndIsActiveTrue(anyString())).thenReturn(Optional.of(getTraveller()));

        Traveller result = underTest.getTravellerByEmail(email);

        verify(travellerRepository).findByEmailAndIsActiveTrue(email);
        assertThat(result.getFirstName()).isEqualTo(getTraveller().getFirstName());
        assertThat(result.getLastName()).isEqualTo(getTraveller().getLastName());
        assertThat(result.getEmail()).isEqualTo(getTraveller().getEmail());
        assertTrue(result.isActive());
    }

    @Test
    void getTravellerByEmailInvalidEmail() {
        String email = "bruno.jacob";

        assertThrows(InvalidEmailException.class, () -> underTest.getTravellerByEmail(email));

        verify(travellerRepository, times(0)).findByEmailAndIsActiveTrue(email);
    }

    @Test
    void getTravellerByEmailNotFound() {
        String email = "bruno.jacob@portugal.pt";

        when(travellerRepository.findByEmailAndIsActiveTrue(anyString())).thenReturn(Optional.empty());

        assertThrows(TravellerNotFoundException.class, () -> underTest.getTravellerByEmail(email));
        verify(travellerRepository).findByEmailAndIsActiveTrue(email);
    }

    @Test
    void getTravellerByMobile() {
        int mobile = 934441231;

        when(travellerRepository.findByMobileNumberAndIsActiveTrue(anyInt())).thenReturn(Optional.of(getTraveller()));

        Traveller result = underTest.getTravellerByMobile(mobile);

        verify(travellerRepository).findByMobileNumberAndIsActiveTrue(mobile);
        assertThat(result.getFirstName()).isEqualTo(getTraveller().getFirstName());
        assertThat(result.getLastName()).isEqualTo(getTraveller().getLastName());
        assertThat(result.getEmail()).isEqualTo(getTraveller().getEmail());
        assertTrue(result.isActive());
    }

    @Test
    void getTravellerByMobileNotFound() {
        int mobile = 934441231;

        when(travellerRepository.findByMobileNumberAndIsActiveTrue(anyInt())).thenReturn(Optional.empty());

        assertThrows(TravellerNotFoundException.class, () -> underTest.getTravellerByMobile(mobile));
        verify(travellerRepository).findByMobileNumberAndIsActiveTrue(mobile);
    }

    @Test
    void getTravellerByDocument() {
        TravellerDocumentDto travellerDocumentDto = TravellerDocumentParser.toDto(getTravellerDocument());

        when(travellerRepository.findOne((Specification<Traveller>) null)).thenReturn(Optional.of(getTraveller()));
        when(travellerSpecification.findOneByDocuments(anyString(), any(), anyString())).thenReturn(null);

        Traveller result = underTest.getTravellerByDocument(travellerDocumentDto);

        verify(travellerSpecification).findOneByDocuments(travellerDocumentDto.documentNumber(), travellerDocumentDto.documentTypeEnum(), travellerDocumentDto.issuingCountry());
        assertThat(result.getFirstName()).isEqualTo(getTraveller().getFirstName());
        assertThat(result.getLastName()).isEqualTo(getTraveller().getLastName());
        assertThat(result.getEmail()).isEqualTo(getTraveller().getEmail());
        assertTrue(result.isActive());
    }

    @Test
    void getTravellerByDocumentNotFound() {
        TravellerDocumentDto travellerDocumentDto = TravellerDocumentParser.toDto(getTravellerDocument());

        when(travellerRepository.findOne((Specification<Traveller>) null)).thenReturn(Optional.empty());
        when(travellerSpecification.findOneByDocuments(anyString(), any(), anyString())).thenReturn(null);

        assertThrows(TravellerNotFoundException.class, () -> underTest.getTravellerByDocument(travellerDocumentDto));
        verify(travellerSpecification).findOneByDocuments(travellerDocumentDto.documentNumber(), travellerDocumentDto.documentTypeEnum(), travellerDocumentDto.issuingCountry());
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
