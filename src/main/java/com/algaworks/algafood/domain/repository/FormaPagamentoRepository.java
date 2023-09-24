package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository {

    List<FormaPagamento> listar();
    FormaPagamento buscarPorId(Long id);
    FormaPagamento salvar(FormaPagamento cozinha);
    void remover(FormaPagamento cozinha);

}
