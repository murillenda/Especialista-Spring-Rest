package com.algaworks.algafood.jpa;

import com.algaworks.algafood.domain.model.Cozinha;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
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

    // Select from cozinha where id := id
    public Cozinha buscar(Long id) {
        return manager.find(Cozinha.class, id);
    }

    // Quando estamos fazendo uma modificação no nosso contexto de persistencia, precisamos de uma transação, se não da erro.
    // Por isso utilizamos a anotação de transactional, para o método ser executado dentro de uma transação
    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        // O método merge não altera a instância que estamos passando como parâmetro.
        // Por exemplo, quando incluimos uma cozinha, um ID é atribuido pelo banco de dados, pois está em AutoIncremento
        // Então este ID não vai estar atribuido a instância que recebemos no método de cozinha, por isso que retornamos
        // o persist da cozinha e não a cozinha que recebemos no método
        Cozinha persist = manager.merge(cozinha);
        return persist;
    }

}
