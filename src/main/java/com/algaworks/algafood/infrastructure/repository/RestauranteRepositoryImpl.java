package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> listar() {
        TypedQuery<Restaurante> query = manager.createQuery("FROM Restaurante", Restaurante.class);
        return query.getResultList();
    }

    @Override
    public Restaurante buscarPorId(Long id)   {
        return manager.find(Restaurante.class, id);
    }

    @Transactional
    @Override
    public Restaurante salvar(Restaurante restaurante) {
        Restaurante persist = manager.merge(restaurante);
        return persist;
    }

    @Transactional
    @Override
    public void remover(Restaurante restaurante) {
        restaurante = buscarPorId(restaurante.getId());
        manager.remove(restaurante);
    }
}
