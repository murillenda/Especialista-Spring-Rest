package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

}
