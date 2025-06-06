package com.impacto.irecon.common.exception.globalexception;

import com.impacto.irecon.common.enums.HttpResponseCode;
import com.impacto.irecon.common.exception.ResourceAlreadyExistException;
import com.impacto.irecon.common.exception.ResourceNotFoundException;
import com.impacto.irecon.common.response.GenericErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return createResponseEntity(
              HttpResponseCode.VALIDATION_FAILED,
                errorMessage
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericErrorResponse> handleResourcesNotFoundException(ResourceNotFoundException ex) {
        return createResponseEntity(ex.getErrorCode(), ex.getMessage());
    }


    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<GenericErrorResponse> handleResourceAlreadyExistException(ResourceAlreadyExistException ex) {
        return createResponseEntity(ex.getErrorCode(), ex.getMessage());
    }


    private static ResponseEntity<GenericErrorResponse> createResponseEntity(HttpResponseCode response, String message) {
        GenericErrorResponse errorResponse = GenericErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(response.getHttpStatus().value())
                .code(response.getCode())
                .error(response.getHttpStatus().getReasonPhrase())
                .message(message != null ? message : response.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, response.getHttpStatus());
    }

//    private static ResponseEntity<GenericErrorResponse> createResponseEntity(ErrorCode errorCode, String message, HttpStatus status) {
//        log.error("Timestamp: {}, Error handling: ErrorCode: {}, ErrorMessage: {}, HttpStatus: {}",
//                LocalDateTime.now(), errorCode.getCode(), message, status);
//        GenericErrorResponse errorResponse = new GenericErrorResponse(errorCode.getCode(), message);
//        return new ResponseEntity<>(errorResponse, status);
//    }


}
