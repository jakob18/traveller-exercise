package org.exercise.travellers.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.exercise.travellers.dto.CreateTravellerDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.entities.TravellerDocument;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.exercise.travellers.exception.DuplicatedResourcesException;
import org.exercise.travellers.exception.TravellerDeactivatedException;
import org.exercise.travellers.exception.TravellerNotFoundException;
import org.exercise.travellers.services.TravellersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TravellerController.class)
@AutoConfigureMockMvc(addFilters = false)
class TravellerControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private TravellersService travellersService;

    protected ObjectMapper objectMapper = new ObjectMapper();
    private final static String CONTROLLER_PATH = "/v1/api/traveller";


    // GET
    @Test
    void getTraveller() throws Exception {
        when(travellersService.getTraveller(anyString())).thenReturn(getEntity());

        final ResultActions result = this.mockMvc.perform(
                get(CONTROLLER_PATH)
                        .param("searchValue", "123")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        String content = result.andReturn().getResponse().getContentAsString();
        TravellerDto getResult = objectMapper.readValue(content, new TypeReference<>() {
        });

        assertThat(getResult.firstName()).isEqualTo("bruno");
        assertThat(getResult.lastName()).isEqualTo("jacob");
        assertThat(getResult.email()).isEqualTo("email@portugal.pt");
        assertThat(getResult.mobileNumber()).isEqualTo(931234567);
        assertThat(getResult.travellerDocumentDto()).isNotNull();
        assertThat(getResult.travellerDocumentDto().documentNumber()).isEqualTo("123456");
    }

    @Test
    void getTravellerNullSearch() throws Exception {
        this.mockMvc.perform(
                get(CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void getTravellerNotFound() throws Exception {
        when(travellersService.getTraveller(anyString())).thenThrow(new TravellerNotFoundException("The traveller was not found"));

        this.mockMvc.perform(
                        get(CONTROLLER_PATH)
                                .param("searchValue", "123")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("The traveller was not found")));
    }

    // ADD
    @Test
    void addTraveller() throws Exception {
        when(travellersService.addTraveller(any())).thenReturn(getEntity());

        final ResultActions result = this.mockMvc.perform(
                post(CONTROLLER_PATH)
                        .content(objectMapper.writeValueAsString(createDto()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        String content = result.andReturn().getResponse().getContentAsString();
        TravellerDto getResult = objectMapper.readValue(content, new TypeReference<>() {
        });

        assertThat(getResult.firstName()).isEqualTo("bruno");
        assertThat(getResult.lastName()).isEqualTo("jacob");
        assertThat(getResult.email()).isEqualTo("email@portugal.pt");
        assertThat(getResult.mobileNumber()).isEqualTo(931234567);
        assertThat(getResult.travellerDocumentDto()).isNotNull();
        assertThat(getResult.travellerDocumentDto().documentNumber()).isEqualTo("123456");
    }

    @Test
    void addTravellerNullBody() throws Exception {
        this.mockMvc.perform(
                post(CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void addTravellerInvalidData() throws Exception {
        when(travellersService.addTraveller(any())).thenThrow(new DuplicatedResourcesException("The email is already exists"));

        this.mockMvc.perform(
                        post(CONTROLLER_PATH)
                                .content(objectMapper.writeValueAsString(createDto()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("The email is already exists")));
    }

    @Test
    void updateTraveller() throws Exception {
        when(travellersService.updateTraveller(any())).thenReturn(getEntity());

        final ResultActions result = this.mockMvc.perform(
                put(CONTROLLER_PATH)
                        .content(objectMapper.writeValueAsString(createDto()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        String content = result.andReturn().getResponse().getContentAsString();
        TravellerDto getResult = objectMapper.readValue(content, new TypeReference<>() {
        });

        assertThat(getResult.firstName()).isEqualTo("bruno");
        assertThat(getResult.lastName()).isEqualTo("jacob");
        assertThat(getResult.email()).isEqualTo("email@portugal.pt");
        assertThat(getResult.mobileNumber()).isEqualTo(931234567);
        assertThat(getResult.travellerDocumentDto()).isNotNull();
        assertThat(getResult.travellerDocumentDto().documentNumber()).isEqualTo("123456");
    }

    @Test
    void updateTravellerNullBody() throws Exception {
        this.mockMvc.perform(
                put(CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void updateTravellerInvalidData() throws Exception {
        when(travellersService.updateTraveller(any())).thenThrow(new TravellerDeactivatedException("The traveller was deleted"));

        this.mockMvc.perform(
                        put(CONTROLLER_PATH)
                                .content(objectMapper.writeValueAsString(createDto()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", is("The traveller was deleted")));
    }

    @Test
    void deleteTraveller() throws Exception {
        doNothing().when(travellersService).deleteTraveller(any());

        this.mockMvc.perform(
                delete(CONTROLLER_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void deleteTravellerNullPathVariable() throws Exception {
        this.mockMvc.perform(
                delete(CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteTravellerInvalidData() throws Exception {
        doThrow(new TravellerNotFoundException("The Traveller was not found")).when(travellersService).deleteTraveller(any());

        this.mockMvc.perform(
                        delete(CONTROLLER_PATH + "/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("The Traveller was not found")));
    }

    private Traveller getEntity() {
        TravellerDocument entityDocument = new TravellerDocument();
        entityDocument.setId(1L);
        entityDocument.setDocumentType(DocumentTypeEnum.ID_CARD);
        entityDocument.setDocumentNumber("123456");
        entityDocument.setIssuingCountry("Portugal");
        entityDocument.setActive(true);

        Traveller entity = new Traveller();
        entity.setId(1L);
        entity.setFirstName("bruno");
        entity.setLastName("jacob");
        entity.setBirthDate(new Date(1982, 01, 19));
        entity.setEmail("email@portugal.pt");
        entity.setMobileNumber(931234567);
        entity.setActive(true);
        entity.setTravellerDocuments(List.of(entityDocument));

        return entity;
    }

    private CreateTravellerDto createDto() {
        return new CreateTravellerDto("bruno", "jacob", new Date(1982, 01, 19), "email@portugal.pt", 931234567, DocumentTypeEnum.ID_CARD, "123456", "Portugal");
    }


}
