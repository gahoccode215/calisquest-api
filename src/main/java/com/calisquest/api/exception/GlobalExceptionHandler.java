
package com.calisquest.api.exception;

import com.calisquest.api.common.response.ApiResponseDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler
        extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1. Handle business exceptions
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleAppException(
            AppException ex) {
        ErrorCode error = ex.getErrorCode();

        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponseDTO.fail(
                        error.getCode(),
                        error.getMessage(),
                        null));
    }

    // 2. Handle validation exceptions
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        Map<String, String> details = new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> details.putIfAbsent(
                        fieldError.getField(),
                        fieldError.getDefaultMessage() != null
                                ? fieldError.getDefaultMessage()
                                : "Giá trị không hợp lệ"));

        ErrorCode error = ErrorCode.VALIDATION_ERROR;

        return ResponseEntity
                .status(error.getStatus())
                .headers(headers)
                .body(ApiResponseDTO.fail(
                        error.getCode(),
                        error.getMessage(),
                        details));
    }

    // 3. Normalize other Spring MVC exceptions
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorCode error = status.is5xxServerError()
                ? ErrorCode.INTERNAL_SERVER_ERROR
                : ErrorCode.INVALID_REQUEST;

        if (status.is5xxServerError()) {
            log.error("Spring MVC server error", ex);
        }

        return ResponseEntity
                .status(status)
                .headers(headers)
                .body(ApiResponseDTO.fail(
                        error.getCode(),
                        error.getMessage(),
                        null));
    }

    // 4. Handle unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleUnexpectedException(Exception ex) {

        log.error("Unexpected server error", ex);

        ErrorCode error = ErrorCode.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDTO.fail(
                        error.getCode(),
                        error.getMessage(),
                        null));
    }
}
