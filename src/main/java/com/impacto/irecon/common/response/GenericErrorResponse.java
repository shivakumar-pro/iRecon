package com.impacto.irecon.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GenericErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
}