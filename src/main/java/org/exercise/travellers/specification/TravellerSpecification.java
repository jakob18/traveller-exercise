package org.exercise.travellers.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.exercise.travellers.entities.Traveller;
import org.exercise.travellers.entities.TravellerDocument;
import org.exercise.travellers.enums.DocumentTypeEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class TravellerSpecification extends BaseSpecification {

    public Specification<Traveller> findOneByDocuments(String documentNumber, DocumentTypeEnum documentType) {
        return findOneByDocuments(documentNumber, documentType, null);
    }

    public Specification<Traveller> findOneByDocuments(String documentNumber, DocumentTypeEnum documentType, String issuingCountry) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Traveller, TravellerDocument> travellerDocumentJoin = root.join("travellerDocuments");

            // Traveller filter
            equalsBoolean("isActive", root, cb, predicates);

            // Traveller Documents filter
            contains(documentNumber, "documentNumber", travellerDocumentJoin, cb, predicates);
            contains(issuingCountry, "issuingCountry", travellerDocumentJoin, cb, predicates);
            equals(documentType, "documentType", travellerDocumentJoin, cb, predicates);
            equalsBoolean("isActive", travellerDocumentJoin, cb, predicates);

            return and(cb, predicates);
        });
    }
}
