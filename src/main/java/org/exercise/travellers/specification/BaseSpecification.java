package org.exercise.travellers.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.exercise.travellers.enums.DocumentTypeEnum;

import java.util.List;

public abstract class BaseSpecification {

    protected  <R> void contains(String value, String entityColumn, Path<R> path, CriteriaBuilder cb, List<Predicate> predicates) {
        if (StringUtils.isNotBlank(value)) {
            predicates.add(cb.like(cb.upper(path.get(entityColumn)), "%" + value.toUpperCase() + "%"));
        }
    }

    protected <R> void equals(DocumentTypeEnum value, String entityColumn, Path<R> path, CriteriaBuilder cb, List<Predicate> predicates) {
        if (null != value) {
            predicates.add(cb.equal(cb.upper(path.get(entityColumn)), String.valueOf(value)));
        }
    }

    protected <R> void equalsBoolean(String entityColumn, Path<R> path, CriteriaBuilder cb, List<Predicate> predicates) {
        predicates.add(cb.equal(path.get(entityColumn), true));
    }

    protected Predicate and(CriteriaBuilder cb, List<Predicate> conditions) {
        return cb.and(conditions.toArray(new Predicate[0]));
    }

}
