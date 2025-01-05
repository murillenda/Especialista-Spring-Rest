package com.algaworks.algafood.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
// Classe para fins educacionais.

// Com essa configuração, podemos apagar o ValidationMessages.properties e utilizar tudo que utilizariamos lá
// no messages.properties.

@Configuration
public class ValidationConfig {

    // LocalValidatorFactoryBean faz a integração e configuração do Bean Validator com o Spring
    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        // Isso aqui estamos indicando que o ValidationMessageSource é um MessageSource
        // Caso não especificamos isso, ele vai usar o ValidationMessages.properties
        // Caso especificamos, ele usa o messages.properties (Spring)
        // na hora do BeanValidation resolver as mensagens.
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

}
