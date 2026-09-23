package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.example.entity.CartEntity;
import org.example.entity.UserEntity;
import org.example.repository.CartRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartRepositoryImpl implements CartRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CartEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CartEntity> criteriaQuery = criteriaBuilder.createQuery(CartEntity.class);
        Root<CartEntity> root = criteriaQuery.from(CartEntity.class);

        root.fetch("cartItems", JoinType.LEFT);
        root.fetch("customer", JoinType.LEFT);

        criteriaQuery.select(root).distinct(true);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<CartEntity> findById(Integer cartId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CartEntity> criteriaQuery = criteriaBuilder.createQuery(CartEntity.class);
        Root<CartEntity> root = criteriaQuery.from(CartEntity.class);

        root.fetch("cartItems", JoinType.LEFT);
        root.fetch("customer", JoinType.LEFT);

        Predicate predicate = criteriaBuilder.equal(root.get("id"), cartId);

        criteriaQuery.where(predicate).distinct(true);

        List<CartEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.getFirst());
        }
    }

    @Override
    public CartEntity save(CartEntity cartEntity) {
        if (cartEntity.getId() == null) {
            entityManager.persist(cartEntity);
        } else {
            entityManager.merge(cartEntity);
        }
        entityManager.flush();

        return cartEntity;
    }

    @Override
    public void deleteById(Integer cartId) {
        Optional<CartEntity> cartEntityOptional = findById(cartId);

        if (cartEntityOptional.isPresent()) {
            CartEntity cartEntity = cartEntityOptional.get();
            entityManager.remove(cartEntity);
        }

    }
}
