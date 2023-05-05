package org.exercise.travellers.dto;

import org.exercise.travellers.enums.DocumentTypeEnum;

public record TravellerDocumentDto(DocumentTypeEnum documentTypeEnum, String documentNumber, String issuingCountry) {
}
