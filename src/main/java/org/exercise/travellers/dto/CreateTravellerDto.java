package org.exercise.travellers.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.exercise.travellers.enums.DocumentTypeEnum;

import java.util.Date;


@AllArgsConstructor
@Getter
@Setter
public class CreateTravellerDto {

    @NotBlank
    @Size(min = 1, max = 50)
    private String firstName;
    @NotBlank
    @Size(min = 1, max = 50)
    private String lastNAme;
    @NotNull
    @Past
    private Date birthDate;
    @NotBlank
    @Email
    private String email;

    @Digits(integer = 15, fraction = 0)
    private int mobileNumber;
    @NotNull
    private DocumentTypeEnum documentTypeEnum;
    @NotBlank
    @Size(min = 1, max = 50)
    private String documentNumber;
    @NotBlank
    @Size(min = 1, max = 50)
    private String issuingCountry;
}
