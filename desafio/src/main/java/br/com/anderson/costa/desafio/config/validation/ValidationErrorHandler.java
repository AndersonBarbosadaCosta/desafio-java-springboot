package br.com.anderson.costa.desafio.config.validation;

import br.com.anderson.costa.desafio.exception.ResourceException;
import br.com.anderson.costa.desafio.model.dto.ErrorMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {

    private final MessageSource messageSource;

    @Autowired
    public ValidationErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorMessageDTO> handle(MethodArgumentNotValidException exception) {

        List<ErrorMessageDTO> dto = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(item -> {
            String message = messageSource.getMessage(item, LocaleContextHolder.getLocale());
            ErrorMessageDTO errorMessage = new ErrorMessageDTO(HttpStatus.BAD_REQUEST.value(), message);
            dto.add(errorMessage);
        });
        return dto;
    }

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity handleException(ResourceException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
