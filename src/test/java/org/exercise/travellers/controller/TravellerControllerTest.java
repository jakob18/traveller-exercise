package org.exercise.travellers.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.exercise.travellers.dto.TravellerDocumentDto;
import org.exercise.travellers.dto.TravellerDto;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.entities.TravellerDocument;
import org.exercise.travellers.enums.DocumentTypeEnum;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    }

    @Test
    void getTravellerNullSearch() throws Exception {
        final ResultActions result = this.mockMvc.perform(
                put(CONTROLLER_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isBadRequest());
    }

    // TODO - more tests

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
        entity.setBirthDate(new Date(System.currentTimeMillis()));
        entity.setEmail("email@portugal.pt");
        entity.setMobileNumber(931234567);
        entity.setActive(true);
        entity.setTravellerDocuments(List.of(entityDocument));

        return entity;
    }


}
