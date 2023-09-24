package com.algaworks.algafood.jpa.restaurante;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class ConsultaRestauranteMain {

    public static void main(String[] args) {
        // Configuração que faz a api rodar e depois já finalizar, para podermos realizar alguns testes
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        // Pegamos o Bean que inicializamos no Cadastro cozinha para podermos realizar as consultas
        RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

        List<Restaurante> restaurantes = restauranteRepository.listar();

        for (Restaurante restaurante : restaurantes) {
            System.out.printf("%s - %f - %s%n", restaurante.getNome(), restaurante.getTaxaFrete(),
                    restaurante.getCozinha().getNome());
        }

    }

}
