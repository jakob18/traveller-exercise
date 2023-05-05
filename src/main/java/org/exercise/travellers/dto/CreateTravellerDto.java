package org.exercise.travellers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.exercise.travellers.enums.DocumentTypeEnum;

import java.util.Date;


@AllArgsConstructor
@Getter
@Setter
public class CreateTravellerDto {

    @NonNull
    private String firstName;
    @NonNull
    private String lastNAme;
    @NonNull
    private Date birthDate;
    @NonNull
    private String email;

    private int mobileNumber;
    @NonNull
    private DocumentTypeEnum documentTypeEnum;
    @NonNull
    private String documentNumber;
    @NonNull
    private String issuingCountry;

}
