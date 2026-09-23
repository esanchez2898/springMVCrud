package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.example.entity.CartEntity;
import org.example.entity.CustomerEntity;
import org.example.repository.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CustomerEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerEntity> criteriaQuery = criteriaBuilder.createQuery(CustomerEntity.class);
        Root<CustomerEntity> root = criteriaQuery.from(CustomerEntity.class);

        root.fetch("address", JoinType.INNER);
        root.fetch("user", JoinType.INNER);
        root.fetch("cart", JoinType.INNER);

        criteriaQuery.select(root).distinct(true);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<CustomerEntity> findById(Integer customerId) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerEntity> criteriaQuery = criteriaBuilder.createQuery(CustomerEntity.class);
        Root<CustomerEntity> root = criteriaQuery.from(CustomerEntity.class);

        root.fetch("address", JoinType.INNER);
        root.fetch("user", JoinType.INNER);
        root.fetch("cart", JoinType.INNER);

        Predicate predicate = criteriaBuilder.equal(root.get("id"), customerId);

        criteriaQuery.where(predicate);

        List<CustomerEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.getFirst());
        }
    }

    @Override
    public Optional<CustomerEntity> findByPhone(String customerPhone) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerEntity> criteriaQuery = criteriaBuilder.createQuery(CustomerEntity.class);
        Root<CustomerEntity> root = criteriaQuery.from(CustomerEntity.class);

        root.fetch("address", JoinType.INNER);
        root.fetch("user", JoinType.INNER);
        root.fetch("cart", JoinType.INNER);

        Predicate predicate = criteriaBuilder.equal(root.get("customerPhone"), customerPhone);

        criteriaQuery.where(predicate);

        List<CustomerEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.getFirst());
        }
    }

    @Override
    public CustomerEntity save(CustomerEntity customerEntity) {
        if (customerEntity.getId() == null) {
            entityManager.persist(customerEntity);
        } else {
            entityManager.merge(customerEntity);
        }
        entityManager.flush();

        return customerEntity;
    }

    @Override
    public void deleteById(Integer customerId) {
        Optional<CustomerEntity> customerEntityOptional = findById(customerId);

        if (customerEntityOptional.isPresent()) {
            CustomerEntity customerEntity = customerEntityOptional.get();
            entityManager.remove(customerEntity);
        }

    }
}
