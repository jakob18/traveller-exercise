package org.exercise.travellers.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "traveller_document")
public class TravellerDocument implements Serializable {

    public static final String SPEC_IS_ACTIVE = "isActive";
    public static final String SPEC_DOCUMENT_TYPE = "documentType";
    public static final String SPEC_DOCUMENT_NUMBER = "documentNumber";
    public static final String SPEC_ISSUING_COUNTRY = "issuingCountry";

    @Serial
    private static final long serialVersionUID = -8597868587019861846L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOCUMENT_TYPE", nullable = false, updatable = false)
    private DocumentTypeEnum documentType;

    @Column(name = "DOCUMENT_NUMBER", nullable = false, updatable = false)
    private String documentNumber;

    @Column(name = "ISSUING_COUNTRY", nullable = false, updatable = false)
    private String issuingCountry;

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "TRAVELLER_ID", referencedColumnName = "id", nullable = false)
    private Traveller traveller;
}
