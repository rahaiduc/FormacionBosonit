package com.block7crudvalidation.repository;

import com.block7crudvalidation.controller.dto.outputs.PersonOutputDto;
import com.block7crudvalidation.domain.Persona;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PersonRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public List<PersonOutputDto> getGreaterQuery(
            HashMap<String, Object> conditions) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Persona> query = cb.createQuery(Persona.class);
        Root<Persona> root = query.from(Persona.class);

        List<Predicate> predicates = new ArrayList<>();

        conditions.forEach((field, value) -> {
            switch (field) {
                case "usuario":
                    predicates.add(cb.greaterThan(root.get(field),(String) conditions.get("dateCondition")));
                    break;
                case "name":
                    predicates.add(cb.greaterThan(root.get(field),(String) conditions.get("dateCondition")));
                    break;
                case "surname":
                    predicates.add(cb.greaterThan(root.get(field),(String) conditions.get("dateCondition")));

                    break;
                case "createdDate":
                    predicates.add(cb.greaterThan(root.get(field),(Date) conditions.get("dateCondition")));
                    break;
            }
        });
        query.select(root)
                .where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager
                .createQuery(query)
                .getResultList()
                .stream()
                .map(Persona::personToPersonOutputDto)
                .toList();
    }

    public List<PersonOutputDto> getLessQuery(
            HashMap<String, Object> conditions) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Persona> query = cb.createQuery(Persona.class);
        Root<Persona> root = query.from(Persona.class);

        List<Predicate> predicates = new ArrayList<>();

        conditions.forEach((field, value) -> {
            switch (field) {
                case "usuario":
                    predicates.add(cb.lessThan(root.get(field),(String) conditions.get("dateCondition")));
                    break;
                case "name":
                    predicates.add(cb.lessThan(root.get(field),(String) conditions.get("dateCondition")));
                    break;
                case "surname":
                    predicates.add(cb.lessThan(root.get(field),(String) conditions.get("dateCondition")));

                    break;
                case "createdDate":
                    predicates.add(cb.lessThan(root.get(field),(Date) conditions.get("dateCondition")));
                    break;
            }
        });
        query.select(root)
                .where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager
                .createQuery(query)
                .getResultList()
                .stream()
                .map(Persona::personToPersonOutputDto)
                .toList();
    }

}
