package com.algaworks.algafood;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// Aqui com esta anotação estou falando que esta classe é uma classe responsável para receber requisições WEB
@Controller
public class PrimeiroController {

    @GetMapping("/hello") // Indicando o caminho dela e falando que é um get
    @ResponseBody // Indicamos que a String vai ser devolvido como resposta da requisição
    public String hello() {
        return "Olá!";
    }

}
