package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> listar();
    Restaurante buscarPorId(Long id);
    Restaurante salvar(Restaurante restaurante);
    void remover(Restaurante restaurante);

}
