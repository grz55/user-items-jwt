package com.grz55.useritems.service.impl;

import com.grz55.useritems.dto.*;
import com.grz55.useritems.entity.UserEntity;
import com.grz55.useritems.exception.InvalidAuthenticationException;
import com.grz55.useritems.exception.UserAlreadyExistsException;
import com.grz55.useritems.mapper.UserMapper;
import com.grz55.useritems.message.AuthenticationMessages;
import com.grz55.useritems.message.UserMessages;
import com.grz55.useritems.repository.UserRepository;
import com.grz55.useritems.security.JWTUtil;
import com.grz55.useritems.service.IAuthorizationService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationServiceImpl implements IAuthorizationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;
  private final JWTUtil jwtUtil;

  @Override
  public void register(RegistrationRequestDTO registrationRequest) {
    if (userRepository.existsByLogin(registrationRequest.getLogin())) {
      throw new UserAlreadyExistsException(
          UserMessages.USER_ALREADY_EXISTS + registrationRequest.getLogin());
    }

    UserEntity user =
        userMapper.toUserEntity(
            registrationRequest.getLogin(),
            passwordEncoder.encode(registrationRequest.getPassword()));
    userRepository.save(user);
  }

  @Override
  public LoginResponseDTO login(LoginRequestDTO loginRequest) {
    Optional<UserEntity> user = userRepository.findByLogin(loginRequest.getLogin());
    if (user.isPresent()
        && passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
      return new LoginResponseDTO(jwtUtil.generateToken(user.get().getLogin()));
    }
    throw new InvalidAuthenticationException(
        AuthenticationMessages.INVALID_AUTHENTICATION_CREDENTIALS);
  }
}
