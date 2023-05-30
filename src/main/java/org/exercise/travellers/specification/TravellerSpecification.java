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
			equalsBoolean(Traveller.SPEC_IS_ACTIVE, root, cb, predicates);

			// Traveller Documents filter
			contains(documentNumber, TravellerDocument.SPEC_DOCUMENT_NUMBER, travellerDocumentJoin, cb, predicates);
			contains(issuingCountry, TravellerDocument.SPEC_ISSUING_COUNTRY, travellerDocumentJoin, cb, predicates);
			equals(documentType, TravellerDocument.SPEC_DOCUMENT_TYPE, travellerDocumentJoin, cb, predicates);
			equalsBoolean(TravellerDocument.SPEC_IS_ACTIVE, travellerDocumentJoin, cb, predicates);

			return and(cb, predicates);
		});
	}
}
