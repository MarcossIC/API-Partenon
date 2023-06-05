package dev.partenon.security.infrastructure.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.partenon.security.domain.Errors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Order(-2)
@Slf4j
@RequiredArgsConstructor
public class ApiExceptionHandler implements HandlerExceptionResolver {
    private final ObjectMapper objectMapper;

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {
        if (ex instanceof BindException) {
            log.debug("Validation error: {}", ex.getMessage());
            //Map<String, String> errors = new HashMap<>();
            var errors = new Errors("validation_failure", "Validation failed.");
            if (ex instanceof MethodArgumentNotValidException) {
                var methodArgumentNotValidEx = (MethodArgumentNotValidException) ex;
                methodArgumentNotValidEx.getBindingResult()
                        .getFieldErrors()
                        .forEach(fieldError ->
                                errors.add(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage()));
            } else if (ex instanceof BindException) {
                var bindException = (BindException) ex;
                bindException.getBindingResult().getFieldErrors()
                        .forEach(fieldError ->
                                errors.add(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage()));
            }
            try {
                response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(objectMapper.writeValueAsString(errors));
            } catch (JsonProcessingException e) {
                log.error("Error serializing validation errors: {}", e.getMessage());
            } catch (Exception e) {
                log.error("Error writing validation errors to response: {}", e.getMessage());
            }
        } else if (ex instanceof RuntimeException) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }

        return new ModelAndView();
    }
}
