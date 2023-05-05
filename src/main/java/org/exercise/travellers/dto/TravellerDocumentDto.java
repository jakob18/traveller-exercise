package org.exercise.travellers.dto;

import org.exercise.travellers.enums.DocumentTypeEnum;

public record TravellerDocumentDto(DocumentTypeEnum documentTypeEnum, int documentNumber, String issuingCountry) {
}
