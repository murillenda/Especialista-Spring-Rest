package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    NEGOCIO("/erro-negocio", "Violação da regra de negócio");


    private final String uri;
    private final String title;

    ProblemType(String path, String title) {
        this.uri = "https://localhost:8080" + path;
        this.title = title;
    }
}
