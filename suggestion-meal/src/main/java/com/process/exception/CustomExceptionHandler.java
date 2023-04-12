package com.process.exception;

import com.process.model.Enum.ResponseStatus;
import com.process.model.dto.response.ApiResponse;
import com.process.util.Translator;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex) {
        log.error("Exception: ", ex);
        return this.createResponse(ResponseStatus.INTERNAL_GENERAL_SERVER_ERROR.code, ex.getMessage());
    }

    @ExceptionHandler({WrongDataException.class})
    public final ResponseEntity<ApiResponse<Object>> handleValidationExceptions(WrongDataException ex) {
        return this.createResponse(ex.getCode(), String.format(Translator.toLocale("EXCEPTION_WRONG_DATA"), ex.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return this.createResponse(ResponseStatus.VALIDATION_ERROR.code, ex.getMessage(), errors);
    }

    private ResponseEntity<ApiResponse<Object>> createResponse(String code, String message) {
        return ResponseEntity.ok().body(new ApiResponse<>(code, message));
    }

    private ResponseEntity<Object> createResponse(String code, String message, Map<String, String> errors) {
        return ResponseEntity.ok().body(new ApiResponse<>(code, message, errors));
    }

}
