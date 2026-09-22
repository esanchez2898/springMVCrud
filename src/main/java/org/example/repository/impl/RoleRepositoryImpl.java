package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.example.entity.ProductEntity;
import org.example.entity.RoleEntity;
import org.example.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RoleEntity> findAll() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RoleEntity> criteriaQuery = criteriaBuilder.createQuery(RoleEntity.class);
        Root<RoleEntity> root = criteriaQuery.from(RoleEntity.class);

        root.fetch("users", JoinType.LEFT);

        criteriaQuery.select(root).distinct(true);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<RoleEntity> findById(Integer roleId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RoleEntity> criteriaQuery = criteriaBuilder.createQuery(RoleEntity.class);
        Root<RoleEntity> root = criteriaQuery.from(RoleEntity.class);

        root.fetch("users", JoinType.LEFT);

        Predicate predicate = criteriaBuilder.equal(root.get("id"), roleId);

        criteriaQuery.where(predicate);//.distinct(true);
        List<RoleEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.getFirst());
        }
    }

    @Override
    public Optional<RoleEntity> findByName(Integer roleName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RoleEntity> criteriaQuery = criteriaBuilder.createQuery(RoleEntity.class);
        Root<RoleEntity> root = criteriaQuery.from(RoleEntity.class);

        root.fetch("users", JoinType.LEFT);

        Predicate predicate = criteriaBuilder.equal(root.get("roleName"), roleName);

        criteriaQuery.where(predicate);//.distinct(true);
        List<RoleEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.getFirst());
        }
    }

    @Override
    public RoleEntity save(RoleEntity roleEntity) {
        if (roleEntity.getId() == null) {
            entityManager.persist(roleEntity);
        } else {
            entityManager.merge(roleEntity);
        }
        entityManager.flush();

        return roleEntity;
    }

    @Override
    public void deleteById(Integer roleId) {
        Optional<RoleEntity> roleEntityOptional = findById(roleId);

        if (roleEntityOptional.isPresent()) {
            RoleEntity roleEntity = roleEntityOptional.get();
            entityManager.remove(roleEntity);
        }

    }
}
