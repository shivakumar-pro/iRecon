package com.impacto.irecon.common.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SuccessResponse<T> {
    private LocalDateTime timestamp;
    private Integer statusCode;
    private String message;
    private T data;
}
