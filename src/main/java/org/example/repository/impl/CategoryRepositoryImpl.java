package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.example.entity.CategoryEntity;
import org.example.entity.ProductEntity;
import org.example.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CategoryEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CategoryEntity> criteriaQuery = criteriaBuilder.createQuery(CategoryEntity.class);
        Root<CategoryEntity> root = criteriaQuery.from(CategoryEntity.class);

        root.fetch("products", JoinType.LEFT);

        criteriaQuery.select(root).distinct(true);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<CategoryEntity> findById(Integer categoryId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CategoryEntity> criteriaQuery = criteriaBuilder.createQuery(CategoryEntity.class);
        Root<CategoryEntity> root = criteriaQuery.from(CategoryEntity.class);

        root.fetch("products", JoinType.LEFT);

        Predicate predicate = criteriaBuilder.equal(root.get("id"), categoryId);

        criteriaQuery.where(predicate).distinct(true);
        List<CategoryEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.getFirst());
        }
    }

    @Override
    public Optional<CategoryEntity> findByName(String nameCategory) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CategoryEntity> criteriaQuery = criteriaBuilder.createQuery(CategoryEntity.class);
        Root<CategoryEntity> root = criteriaQuery.from(CategoryEntity.class);

        root.fetch("products", JoinType.LEFT);

        Predicate predicate = criteriaBuilder.equal(root.get("nameCategory"), nameCategory);

        criteriaQuery.where(predicate).distinct(true);
        List<CategoryEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.getFirst());
        }
    }

    @Override
    public CategoryEntity save(CategoryEntity categoryEntity) {
        if (categoryEntity.getId() == null) {
            entityManager.persist(categoryEntity);
        } else {
            entityManager.merge(categoryEntity);
        }
        entityManager.flush();

        return categoryEntity;
    }

    @Override
    public void deleteById(Integer categoryId) {
        Optional<CategoryEntity> categoryEntityOptional = findById(categoryId);

        if (categoryEntityOptional.isPresent()) {
            CategoryEntity categoryEntity = categoryEntityOptional.get();
            entityManager.remove(categoryEntity);
        }

    }
}
