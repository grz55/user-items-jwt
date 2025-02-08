package com.grz55.useritems.messages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthenticationMessages {

  public static final String AUTHENTICATION_IS_MISSING_OR_INVALID =
      "Authentication is missing or invalid";
  public static final String INVALID_AUTHENTICATION_CREDENTIALS =
      "Invalid authentication credentials";
}
