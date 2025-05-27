package com.impacto.irecon.common.exception.globalexception;

import com.impacto.irecon.common.exception.RuleNotFoundException;
import com.impacto.irecon.common.response.GenericErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandlerV1 {

    @ExceptionHandler(RuleNotFoundException.class)
    public ResponseEntity<GenericErrorResponse> handleRuleNotFoundException(RuleNotFoundException ex, HttpServletRequest request) {
        log.error("Rule not found: {}", ex.getMessage());
        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    private ResponseEntity<GenericErrorResponse> createErrorResponse(HttpStatus status, String message, String path) {
        GenericErrorResponse errorResponse = new GenericErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );
        return new ResponseEntity<>(errorResponse, status);
    }
}
