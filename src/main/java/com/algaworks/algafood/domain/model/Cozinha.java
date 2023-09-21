package com.algaworks.algafood.domain.model;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Cozinha {

    // Identity significa que estamos passando a responsabilidade de gerar o valor do identificador
    // pro provedor de prsistencia, nesse caso pro banco de dados MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cozinha cozinha = (Cozinha) o;
        return getId().equals(cozinha.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
