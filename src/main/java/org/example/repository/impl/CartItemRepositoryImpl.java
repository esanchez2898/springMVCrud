package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.example.entity.CartItemEntity;
import org.example.repository.CartItemRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartItemRepositoryImpl implements CartItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CartItemEntity> findAllByCartId(Integer cartId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CartItemEntity> criteriaQuery = criteriaBuilder.createQuery(CartItemEntity.class);
        Root<CartItemEntity> root = criteriaQuery.from(CartItemEntity.class);

        root.fetch("product", JoinType.LEFT);
        root.fetch("cart", JoinType.LEFT);

        Predicate predicate = criteriaBuilder.equal(root.get("cart").get("id"), cartId);

        criteriaQuery.where(predicate).distinct(true);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<CartItemEntity> findById(Integer itemId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CartItemEntity> criteriaQuery = criteriaBuilder.createQuery(CartItemEntity.class);
        Root<CartItemEntity> root = criteriaQuery.from(CartItemEntity.class);

        root.fetch("product", JoinType.LEFT);
        root.fetch("cart", JoinType.LEFT);

        Predicate predicate = criteriaBuilder.equal(root.get("id"), itemId);

        criteriaQuery.where(predicate).distinct(true);

        List<CartItemEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.getFirst());
        }
    }

    @Override
    public CartItemEntity save(CartItemEntity itemEntity) {
        if (itemEntity.getId() == null) {
            entityManager.persist(itemEntity);
        } else {
            entityManager.merge(itemEntity);
        }
        entityManager.flush();

        return itemEntity;
    }


    @Override
    public void deleteById(Integer itemId) {
        Optional<CartItemEntity> cartItemEntityOptional = findById(itemId);
        cartItemEntityOptional.ifPresent(entityManager::remove);

    }
}
