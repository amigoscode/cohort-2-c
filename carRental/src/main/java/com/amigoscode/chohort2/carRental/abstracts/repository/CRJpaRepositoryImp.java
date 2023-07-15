package com.amigoscode.chohort2.carRental.abstracts.repository;

import com.amigoscode.chohort2.carRental.abstracts.AbstractAuditingEntity;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

public class CRJpaRepositoryImp<T extends AbstractAuditingEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements CRJpaRepository<T, ID> {

    private final EntityManager entityManager;

    public CRJpaRepositoryImp(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }


    @Transactional
    @Override
    public T saveAndRefresh(T entity) {
        entity = save(entity);
        entityManager.refresh(entity);
        return entity;
    }

    @Transactional
    public T merge (T updatedEntity ) {
        return entityManager.merge(updatedEntity);

    }
}
