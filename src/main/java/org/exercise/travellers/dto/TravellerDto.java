package org.exercise.travellers.dto;

import java.util.Date;

public record TravellerDto(Long id, String firstName, String lastName, Date birthDate, String email, int mobileNumber, TravellerDocumentDto travellerDocumentDto) {
}
