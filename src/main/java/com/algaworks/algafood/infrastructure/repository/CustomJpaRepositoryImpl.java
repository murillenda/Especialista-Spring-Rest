package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.repository.CustomJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.Optional;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

    private EntityManager manager;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.manager = entityManager;
    }

    @Override
    public Optional<T> buscarPrimeiro() {
        var jpql = "FROM " + getDomainClass().getName();

        // Gera uma consulta SQL limitando o resultado em apenas 1 linha
        T entity = manager.createQuery(jpql, getDomainClass())
                .setMaxResults(1)
                .getSingleResult();

        return Optional.ofNullable(entity);
    }
}
