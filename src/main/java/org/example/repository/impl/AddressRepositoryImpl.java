package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.FetchType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.example.entity.AddressEntity;
import org.example.entity.CategoryEntity;
import org.example.repository.AddressRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AddressRepositoryImpl implements AddressRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<AddressEntity> findAll() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AddressEntity> criteriaQuery = criteriaBuilder.createQuery(AddressEntity.class);
        Root<AddressEntity> root = criteriaQuery.from(AddressEntity.class);

        root.fetch("customer", JoinType.LEFT);

        criteriaQuery.select(root).distinct(true);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<AddressEntity> findById(Integer addressId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AddressEntity> criteriaQuery = criteriaBuilder.createQuery(AddressEntity.class);
        Root<AddressEntity> root = criteriaQuery.from(AddressEntity.class);

        root.fetch("customer", JoinType.LEFT);

        Predicate predicate = criteriaBuilder.equal(root.get("id"), addressId);

        criteriaQuery.where(predicate).distinct(true);

        List<AddressEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.getFirst());
        }

    }


    @Override
    public AddressEntity save(AddressEntity addressEntity) {

        if (addressEntity.getId() == null) {
            entityManager.persist(addressEntity);
        } else {
            entityManager.merge(addressEntity);
        }
        entityManager.flush();

        return addressEntity;
    }

    @Override
    public void deleteById(Integer addressId) {
        Optional<AddressEntity> addressEntityOptional = findById(addressId);

        if (addressEntityOptional.isPresent()) {
            AddressEntity addressEntity = addressEntityOptional.get();
            entityManager.remove(addressEntity);
        }

    }
}
