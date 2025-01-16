package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
class CadastroCozinhaIT {

    private static final int COZINHA_INEXISTENTE = 100;

    private Cozinha cozinhaAmericana;
    private int quantidadeCozinhasCadastradas;
    private String jsonCorretoCozinhaChinesa;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @BeforeEach
    void setUp() {
        // Habilita os logs caso falhe para melhor análise
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        // Seta a porta por conta dos testes do database
        RestAssured.port = port;
        // BasePath
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables(); // Limpando dados das tabelas do banco de testes
        prepararDados();
        jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResourcePath(
                "src/test/resources/json/correto/cozinha-chinesa.json");
    }

    @Test
    void deveRetornarStatus200_QuandoConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
                // Matchers da biblioteca hamcrest
            .body("", hasSize(quantidadeCozinhasCadastradas)) // Validando se tem 2 objetos na resposta (pelo body ser vazio ali).
            .body("nome", hasItems("Tailandesa", "Americana")); // Validando que tem campos "nome" com esses valores.
    }

    @Test
    void deveRetornarStatus201_QuandoCadastrarCozinha() {
        given()
            .body(jsonCorretoCozinhaChinesa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
        given()
            .pathParam("cozinhaId", cozinhaAmericana.getId()) // Passando o PathParam do cozinhaId
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo("Americana"));
    }

    @Test
    void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
        given()
            .pathParam("cozinhaId", COZINHA_INEXISTENTE) // Passando o PathParam do cozinhaId
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        Cozinha cozinhaTailandesa = new Cozinha();
        cozinhaTailandesa.setNome("Tailandesa");
        cozinhaRepository.save(cozinhaTailandesa);

        cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);

        quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
    }

}
