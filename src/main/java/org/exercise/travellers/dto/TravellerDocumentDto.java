package org.exercise.travellers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.exercise.travellers.enums.DocumentTypeEnum;

public record TravellerDocumentDto(
        @NotNull
        DocumentTypeEnum documentTypeEnum,
        @NotBlank
        @Size(min = 1, max = 50)
        String documentNumber,
        @NotBlank
        @Size(min = 1, max = 50)
        String issuingCountry) {
}
