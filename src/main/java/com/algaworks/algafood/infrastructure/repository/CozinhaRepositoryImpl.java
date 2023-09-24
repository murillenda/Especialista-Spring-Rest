package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cozinha> listar() {
        TypedQuery<Cozinha> query = manager.createQuery("FROM Cozinha", Cozinha.class);
        return query.getResultList();
    }

    @Override
    public Cozinha buscarPorId(Long id)   {
        return manager.find(Cozinha.class, id);
    }

    @Transactional
    @Override
    public Cozinha salvar(Cozinha cozinha) {
        Cozinha persist = manager.merge(cozinha);
        return persist;
    }

    @Transactional
    @Override
    public void remover(Cozinha cozinha) {
        cozinha = buscarPorId(cozinha.getId());
        manager.remove(cozinha);
    }
}