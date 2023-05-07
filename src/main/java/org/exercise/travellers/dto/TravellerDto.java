package org.exercise.travellers.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record TravellerDto(
        @NotNull
        Long id,
        @NotBlank
        @Size(min = 1, max = 50)
        String firstName,
        @NotBlank
        @Size(min = 1, max = 50)
        String lastName,
        @NotNull
        Date birthDate,
        @NotBlank
        @Email
        String email,
        @Digits(integer = 15, fraction = 0)
        int mobileNumber,
        @NotNull
        TravellerDocumentDto travellerDocumentDto) {
}
