package com.algaworks.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

// Anotação fala que essa interface não deve ser levado em conta para fins de instanciação de um repositório pelo SDJ
// Ou seja, o SDJ não deve instanciar uma implementação para esta interface, ele deve ignorar.
@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

    Optional<T> buscarPrimeiro();

}
