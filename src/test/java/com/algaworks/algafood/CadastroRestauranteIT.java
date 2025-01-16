package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
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

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
class CadastroRestauranteIT {

    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação da regra de negócio";

    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";

    private static final int RESTAURANTE_ID_INEXISTENTE = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private String jsonRestauranteCorreto;
    private String jsonRestauranteCorretoComTaxaFreteZeradaComFreteGratisDescricao;
    private String jsonRestauranteIncorretoCozinhaInexistente;
    private String jsonRestauranteIncorretoSemCozinha;
    private String jsonRestauranteIncorretoSemTaxaFrete;
    private String jsonRestauranteIncorretoComTaxaFreteNegativa;
    private String jsonRestauranteIncorretoComTaxaFreteZeradaSemFreteGratisDescricao;

    private Restaurante burgerTopRestaurante;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";
        databaseCleaner.clearTables();
        prepararDados();

        jsonRestauranteCorreto = ResourceUtils.getContentFromResourcePath(
                "src/test/resources/json/correto/restaurante-new-york-barbecue.json");

        jsonRestauranteCorretoComTaxaFreteZeradaComFreteGratisDescricao = ResourceUtils.getContentFromResourcePath(
                "src/test/resources/json/correto/restaurante-new-york-barbecue-com-taxa-frete-zerada-com-frete-gratis-descricao.json");

        jsonRestauranteIncorretoCozinhaInexistente = ResourceUtils.getContentFromResourcePath(
                "src/test/resources/json/incorreto/restaurante-new-york-barbecue-com-cozinha-inexistente.json");

        jsonRestauranteIncorretoSemCozinha = ResourceUtils.getContentFromResourcePath(
                "src/test/resources/json/incorreto/restaurante-new-york-barbecue-sem-cozinha.json");

        jsonRestauranteIncorretoSemTaxaFrete = ResourceUtils.getContentFromResourcePath(
                "src/test/resources/json/incorreto/restaurante-new-york-barbecue-sem-taxa-frete.json");

        jsonRestauranteIncorretoComTaxaFreteNegativa = ResourceUtils.getContentFromResourcePath(
                "src/test/resources/json/incorreto/restaurante-new-york-barbecue-com-taxa-frete-negativa.json");

        jsonRestauranteIncorretoComTaxaFreteZeradaSemFreteGratisDescricao = ResourceUtils.getContentFromResourcePath(
                "src/test/resources/json/incorreto/restaurante-new-york-barbecue-com-taxa-frete-zerada-sem-frete-gratis-descricao.json");
    }

    // Cadastro Restaurante correto
    @Test
    void deveRetornarStatus201_QuandoCadastrarRestaurante() {
        given()
            .body(jsonRestauranteCorreto)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void deveRetornarStatus201_QuandoCadastrarRestauranteComTaxaFreteZeradaComFreteGratisDescricao() {
        given()
            .body(jsonRestauranteCorretoComTaxaFreteZeradaComFreteGratisDescricao)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    // Cadastro Restaurante incorreto
    @Test
    void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
        given()
            .body(jsonRestauranteIncorretoCozinhaInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
    }

    @Test
    void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
        given()
            .body(jsonRestauranteIncorretoSemCozinha)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
        given()
            .body(jsonRestauranteIncorretoSemTaxaFrete)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    void deveRetornarStatus400_QuandoCadastrarRestauranteComTaxaFreteNegativa() {
        given()
            .body(jsonRestauranteIncorretoComTaxaFreteNegativa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void deveRetornarStatus400_QuandoCadastrarRestauranteComTaxaFreteZeradaSemFreteGratisDescricao() {
        given()
            .body(jsonRestauranteIncorretoComTaxaFreteZeradaSemFreteGratisDescricao)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    // Consulta restaurante correto
    @Test
    void deveRetornarStatus200_QuandoConsultarRestaurantes() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
        given()
            .pathParam("restauranteId", burgerTopRestaurante.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(burgerTopRestaurante.getNome()));
    }

    // Consulta restaurante incorreto
    @Test
    void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
        given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados() {
        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        Cozinha cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);

        burgerTopRestaurante = new Restaurante();
        burgerTopRestaurante.setNome("Burger Top");
        burgerTopRestaurante.setTaxaFrete(new BigDecimal(10));
        burgerTopRestaurante.setCozinha(cozinhaAmericana);
        restauranteRepository.save(burgerTopRestaurante);

        Restaurante comidaMineiraRestaurante = new Restaurante();
        comidaMineiraRestaurante.setNome("Comida Mineira");
        comidaMineiraRestaurante.setTaxaFrete(new BigDecimal(10));
        comidaMineiraRestaurante.setCozinha(cozinhaBrasileira);
        restauranteRepository.save(comidaMineiraRestaurante);
    }
}
