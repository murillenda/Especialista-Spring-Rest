package com.algaworks.algafood.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int numeroMultiplo;

    // Método que inicializa o validador para preparar para as chamadas futuras do método isValid
    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
        boolean valido = true;

        if (value != null) {
            var valorDecimal = BigDecimal.valueOf(value.doubleValue());
            var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);
            // remainder retorna o resto da divisão de um número por outro
            var resto = valorDecimal.remainder(multiploDecimal); // valorDecimal / multiploDecimial = resto

            valido = resto.compareTo(BigDecimal.ZERO) == 0;
        }

        return valido;
    }

}
