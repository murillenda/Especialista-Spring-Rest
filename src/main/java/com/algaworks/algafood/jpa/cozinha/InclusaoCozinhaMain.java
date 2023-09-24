package com.algaworks.algafood.jpa.cozinha;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class InclusaoCozinhaMain {

    public static void main(String[] args) {
        // Configuração que faz a api rodar e depois já finalizar, para podermos realizar alguns testes
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        // Pegamos o Bean que inicializamos no Cadastro cozinha para podermos realizar as consultas
        CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Brasileira");

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Japonesa");

        // Nesse ponto do código os Id's ainda estão null, após adicionar, irão autoincrementar.
        cozinhaRepository.salvar(cozinha1);
        cozinhaRepository.salvar(cozinha2);

        // Provando que os Id's de cozinha 1 e 2 estão null, pois estamos pegando da variável que instanciamos inicialmente
        System.out.printf("%d - %s\n", cozinha1.getId(), cozinha1.getNome());
        System.out.printf("%d - %s\n", cozinha2.getId(), cozinha2.getNome());
    }

}
