package com.amigoscode.chohort2.carRental.abstracts.repository;

import com.amigoscode.chohort2.carRental.abstracts.AbstractAuditingEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CRJpaRepository<T extends AbstractAuditingEntity, ID extends Serializable> extends JpaRepository<T, ID> {

    @Override
    default void delete(@NotNull T entity) {
        entity.setDeleted(true);
        save(entity);
    }

    @Override
    default void deleteById(@NotNull ID id) {
        findById(id).ifPresent(data -> {
            data.setDeleted(true);
            save(data);
        });
    }

    @Override
    default void deleteAll() {

    }

    @Override
    default void deleteAllById(@NotNull Iterable<? extends ID> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    default void deleteAll(@NotNull Iterable<? extends T> entities) {
        entities
                .forEach(this::delete);
    }

    T saveAndRefresh(T entity);

    T merge (T entity);

}
