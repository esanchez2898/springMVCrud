package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.example.entity.ProductEntity;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserEntity> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);

        root.fetch("roles", JoinType.INNER);

        criteriaQuery.select(root).distinct(true);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<UserEntity> findById(Integer userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);

        root.fetch("roles", JoinType.INNER);

        Predicate predicate = criteriaBuilder.equal(root.get("id"), userId);

        criteriaQuery.where(predicate).distinct(true);

        List<UserEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.getFirst());
        }
    }

    @Override
    public Optional<UserEntity> findByEmail(String userEmail) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);

        root.fetch("roles", JoinType.INNER);

        Predicate predicate = criteriaBuilder.equal(root.get("email"), userEmail);

        criteriaQuery.where(predicate).distinct(true);

        List<UserEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.getFirst());
        }
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            entityManager.persist(userEntity);
        } else {
            entityManager.merge(userEntity);
        }
        entityManager.flush();

        return userEntity;
    }

    @Override
    public void deleteById(Integer userId) {
        Optional<UserEntity> userEntityOptional = findById(userId);

        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            entityManager.remove(userEntity);
        }

    }
}
