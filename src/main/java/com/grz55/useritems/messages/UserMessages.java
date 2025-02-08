package com.grz55.useritems.messages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMessages {

  public static final String USER_NOT_FOUND_BY_USERNAME = "User not found with username: ";
  public static final String USER_ALREADY_EXISTS = "User already exists with username: ";
}
