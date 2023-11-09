package dev.partenon.security.infrastructure.resource;

import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.partenon.global.domain.exceptions.ContentInUseException;
import dev.partenon.global.domain.exceptions.ContentNotFoundException;
import dev.partenon.security.domain.ExceptionCodes;
import dev.partenon.security.domain.ExceptionDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public HttpEntity<Object> handlerIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        var exceptionDto = ExceptionDTO.builder()
                .type(ex.getClass().getTypeName())
                .code(ExceptionCodes.ARGS)
                .details(Map.of("message", ex.getMessage()))
                .build();

        return handleExceptionInternal(ex, exceptionDto, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = ContentInUseException.class)
    public HttpEntity<Object> handlerContentInUseException(ContentInUseException ex, WebRequest request) {
        var exceptionDto = ExceptionDTO.builder()
                .type(ex.getClass().getTypeName())
                .code(ExceptionCodes.IN_USE)
                .details(Map.of("message", ex.getMessage()))
                .build();


        return handleExceptionInternal(ex, exceptionDto, HttpHeaders.EMPTY, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = ContentNotFoundException.class)
    public HttpEntity<Object> handlerContentNotFoundException(ContentNotFoundException ex, WebRequest request) {
        var exceptionDto = ExceptionDTO.builder()
                .type(ex.getClass().getTypeName())
                .code(ExceptionCodes.NOTFOUND)
                .details(Map.of("message", ex.getMessage()))
                .build();


        return handleExceptionInternal(ex, exceptionDto, HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = JWTVerificationException.class)
    public HttpEntity<Object> handlerJWTDecodeException(JWTVerificationException ex, WebRequest request) {
        var exceptionDto = ExceptionDTO.builder()
                .type(ex.getClass().getTypeName())
                .code(ExceptionCodes.JWT)
                .details(Map.of("message", ex.getMessage()))
                .build();


        return handleExceptionInternal(ex, exceptionDto, HttpHeaders.EMPTY, HttpStatus.FORBIDDEN, request);
    }


    //Exception = 1
    @ExceptionHandler(value = Exception.class)
    public HttpEntity<Object> handlerExceptions(Exception ex, WebRequest request) {
        var exceptionDto = ExceptionDTO.builder()
                .type(ex.getClass().getTypeName())
                .code(ExceptionCodes.GENERIC)
                .details(Map.of("message", ex.getMessage()))
                .build();


        return handleExceptionInternal(ex, exceptionDto, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

    //Exception = 10
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String name = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(name, message);
        });

        var exceptionDto = ExceptionDTO.builder()
                .type(ex.getClass().getTypeName())
                .code(ExceptionCodes.INPUT)
                .details(errors)
                .build();

        return handleExceptionInternal(ex, exceptionDto, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }
}
