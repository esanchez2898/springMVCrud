package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.example.entity.ProductEntity;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> criteriaQuery = criteriaBuilder.createQuery(ProductEntity.class);
        Root<ProductEntity> root = criteriaQuery.from(ProductEntity.class);

        root.fetch("category", JoinType.INNER);

        criteriaQuery.select(root);//.distinct(true);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }


    @Override
    public Optional<ProductEntity> findById(Integer id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> criteriaQuery = criteriaBuilder.createQuery(ProductEntity.class);
        Root<ProductEntity> root = criteriaQuery.from(ProductEntity.class);

        root.fetch("category", JoinType.LEFT);

        Predicate predicate = criteriaBuilder.equal(root.get("id"), id);

        criteriaQuery.where(predicate);//.distinct(true);
        List<ProductEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.getFirst());
        }

    }


    @Override
    public Optional<ProductEntity> findByName(String productName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> criteriaQuery = criteriaBuilder.createQuery(ProductEntity.class);
        Root<ProductEntity> root = criteriaQuery.from(ProductEntity.class);

        root.fetch("category", JoinType.LEFT);

        Predicate predicate = criteriaBuilder.equal(root.get("name"), productName);

        criteriaQuery.where(predicate);//.distinct(true);
        List<ProductEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.getFirst());
        }
    }


    @Override
    public List<ProductEntity> findAllByCategoryId(Integer categoryId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> criteriaQuery = criteriaBuilder.createQuery(ProductEntity.class);
        Root<ProductEntity> root = criteriaQuery.from(ProductEntity.class);

        root.fetch("category", JoinType.LEFT);

        Predicate predicate = criteriaBuilder.equal(root.get("category").get("id"), categoryId);

        criteriaQuery.select(root).where(predicate);//.distinct(true);

        return entityManager.createQuery(criteriaQuery).getResultList();

    }


    @Override
    public ProductEntity save(ProductEntity productEntity) {

        if (productEntity.getId() == null) {
            entityManager.persist(productEntity);
        } else {
            entityManager.merge(productEntity);
        }
        entityManager.flush();

        return productEntity;
    }


    @Override
    public void deleteById(Integer productId) {
        Optional<ProductEntity> productEntityOptional = findById(productId);

        if (productEntityOptional.isPresent()) {
            ProductEntity productEntity = productEntityOptional.get();
            entityManager.remove(productEntity);
        }
    }

}