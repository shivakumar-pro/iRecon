package com.impacto.irecon.common.exception;

import com.impacto.irecon.common.enums.HttpResponseCode;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

  private final HttpResponseCode errorCode;

  public ResourceNotFoundException(String message) {
    super(message);
    this.errorCode = HttpResponseCode.NOT_FOUND;
  }

  public ResourceNotFoundException(HttpResponseCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

}
