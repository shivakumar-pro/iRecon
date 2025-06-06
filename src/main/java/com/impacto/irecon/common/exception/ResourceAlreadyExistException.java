package com.impacto.irecon.common.exception;

import com.impacto.irecon.common.enums.HttpResponseCode;
import lombok.Getter;

@Getter
public class ResourceAlreadyExistException extends RuntimeException {

    private final HttpResponseCode errorCode;

    public ResourceAlreadyExistException(HttpResponseCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}