package com.algaworks.algafood.jpa.cozinha;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class BuscaCozinhaMain {

    public static void main(String[] args) {
        // Configuração que faz a api rodar e depois já finalizar, para podermos realizar alguns testes
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        // Pegamos o Bean que inicializamos no Cadastro cozinha para podermos realizar as consultas
        CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

        // Buscando cozinha com id 1
        Cozinha cozinha = cozinhaRepository.buscarPorId(1L);

        System.out.println(cozinha.getNome());

    }

}
