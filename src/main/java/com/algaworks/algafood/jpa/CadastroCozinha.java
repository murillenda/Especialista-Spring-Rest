package com.algaworks.algafood.jpa;

import com.algaworks.algafood.domain.model.Cozinha;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CadastroCozinha {

    // Utilizamos essa anotação para injetar o entity manager aqui
    @PersistenceContext
    private EntityManager manager;

    public List<Cozinha> listar() {
        // Assim realizamos uma query JPQL
        TypedQuery<Cozinha> query = manager.createQuery("FROM Cozinha", Cozinha.class);
        return query.getResultList();
    }

}
