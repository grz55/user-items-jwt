package com.grz55.useritems.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidAuthenticationException extends AuthenticationException {

  public InvalidAuthenticationException(String message) {
    super(message);
  }
}
