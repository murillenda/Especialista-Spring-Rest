package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(groups = Groups.CozinhaId.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    // Bom instanciar a list para evitar NullpointerException
    // Para tratar um relacionamento bidirecional, precisamos tratar a recursividade de alguma forma.
    @JsonIgnore
    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurantes = new ArrayList<>();

}
