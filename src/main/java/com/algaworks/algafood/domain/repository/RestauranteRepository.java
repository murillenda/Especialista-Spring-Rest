package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
        JpaSpecificationExecutor<Restaurante> {

    @Nonnull
    @Query("FROM Restaurante r JOIN FETCH r.cozinha LEFT JOIN FETCH r.formasPagamentos")
    List<Restaurante> findAll();

    List<Restaurante> findTop2ByNomeContaining(String nome);

}
