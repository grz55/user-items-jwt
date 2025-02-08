package com.grz55.useritems.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidAuthenticationException extends AuthenticationException {

  public InvalidAuthenticationException(String message) {
    super(message);
  }
}
