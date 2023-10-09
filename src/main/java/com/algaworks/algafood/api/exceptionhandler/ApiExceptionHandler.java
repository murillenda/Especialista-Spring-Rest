package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import jakarta.annotation.Nonnull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {



    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@Nonnull HttpMessageNotReadableException ex,
                                                                  @Nonnull HttpHeaders headers,
                                                                  @Nonnull HttpStatusCode status,
                                                                  @Nonnull WebRequest request) {
        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof InvalidFormatException rootCauseCasted) {
            return handleInvalidFormatException(rootCauseCasted, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException rootCauseCasted) {
            return handlePropertyBindingException(rootCauseCasted, headers, status, request);
        }

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição está inválido, verifique erro de sintaxe";

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return super.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(@Nonnull InvalidFormatException ex,
                                                                @Nonnull HttpHeaders headers,
                                                                @Nonnull HttpStatusCode status,
                                                                @Nonnull WebRequest request) {

        String property = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' recebeu o valor '%s' que é de um tipo inválido. " +
                "Corrija e informe um valor com o tipo %s", property, ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException e,
                                                                 @Nonnull HttpHeaders headers,
                                                                 @Nonnull HttpStatusCode status,
                                                                 @Nonnull WebRequest request) {

        String property = joinPath(e.getPath());

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade %s não existe. " +
                "Corrija ou remova essa propriedade e tente novamente.", property);

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(e, problem, headers, status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.NEGOCIO;

        Problem problem = createProblemBuilder(status, problemType, e.getMessage())
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e,
                                                                  WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;

        Problem problem = createProblemBuilder(status, problemType, e.getMessage())
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }


    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;

        Problem problem = createProblemBuilder(status, problemType, e.getMessage())
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@Nonnull Exception ex, Object body,
                                                             @Nonnull HttpHeaders headers,
                                                             @Nonnull HttpStatusCode statusCode,
                                                             @Nonnull WebRequest request) {

        if (body == null) {
            body = Problem.builder()
                    .title(HttpStatus.valueOf(statusCode.value()).getReasonPhrase())
                    .status(statusCode.value())
                    .build();
        } else if (body instanceof String bodyString) {
            body = Problem.builder()
                    .title(bodyString)
                    .status(statusCode.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    // Retornando um builder do problem, ainda não é o Problem
    // Retornamos o builder e não um Problem, pois se quisermos adicionar outras propriedades, adicionamos no método la
    // em cima
    private Problem.ProblemBuilder createProblemBuilder(HttpStatusCode status, ProblemType problemType, String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}
