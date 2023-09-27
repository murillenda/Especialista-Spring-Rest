package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

// A função dessa classe é empacotar, embrulhar uma lista de cozinhas.
@JacksonXmlRootElement(localName = "cozinhas") // também podemos utilizar o @JsonRootElement, que também funcionaria.
@Data
public class CozinhasXmlWrapper {

    @JsonProperty("cozinha")
    @JacksonXmlElementWrapper(useWrapping = false) // Desabilitando o embrulho, ou seja, não vai duplicar na hora da saída o valor (ele duplica pois mostra a LISTA que criamos de cozinhas também, assim desabilitamos essa parte)
    @NonNull // Anotamos como NonNull, pois o data ele também gera construtores com propriedades obrigatórias, assim a gente deixa essa propriedade obrigatória
    private List<Cozinha> cozinhas;

}
