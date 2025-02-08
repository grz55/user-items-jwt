package com.grz55.useritems.mapper;

import com.grz55.useritems.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserEntity toUserEntity(String login, String encodedPassword) {
    return new UserEntity(login, encodedPassword);
  }
}
