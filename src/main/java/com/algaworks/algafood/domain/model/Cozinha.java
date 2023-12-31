package com.algaworks.algafood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@JsonRootName("cozinha")
@Data
// Cria os equals and hash code somente nos que eu colocar a anotação.
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {

    // Identity significa que estamos passando a responsabilidade de gerar o valor do identificador
    // pro provedor de prsistencia, nesse caso pro banco de dados MySQL
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    // Bom instanciar a list para evitar NullpointerException
    // Para tratar um relacionamento bidirecional, precisamos tratar a recursividade de alguma forma.
    @JsonIgnore
    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurantes = new ArrayList<>();

}
