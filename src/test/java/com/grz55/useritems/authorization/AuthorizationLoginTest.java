package com.grz55.useritems.authorization;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.grz55.useritems.dto.LoginRequestDTO;
import com.grz55.useritems.dto.LoginResponseDTO;
import com.grz55.useritems.entity.UserEntity;
import com.grz55.useritems.exception.InvalidAuthenticationException;
import com.grz55.useritems.message.AuthenticationMessages;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthorizationLoginTest extends AuthorizationServiceBaseTest {

  private LoginRequestDTO loginRequest;
  private UserEntity userEntity;

  @BeforeEach
  void setUp() {
    loginRequest = new LoginRequestDTO();
    loginRequest.setLogin("user");
    loginRequest.setPassword("password");

    userEntity = new UserEntity();
    userEntity.setLogin("user");
    userEntity.setPassword("hashedPassword");
  }

  @Test
  void shouldReturnTokenIfCredentialsCorrect() {
    when(userRepository.findByLogin(loginRequest.getLogin())).thenReturn(Optional.of(userEntity));
    when(passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword()))
        .thenReturn(true);
    when(jwtUtil.generateToken(userEntity.getLogin())).thenReturn("newToken");

    LoginResponseDTO response = authorizationService.login(loginRequest);

    assertNotNull(response);
    assertEquals("newToken", response.getToken());
  }

  @Test
  void shouldThrowExceptionIfPasswordIncorrect() {
    when(userRepository.findByLogin(loginRequest.getLogin())).thenReturn(Optional.of(userEntity));
    when(passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword()))
        .thenReturn(false);

    InvalidAuthenticationException exception =
        assertThrows(
            InvalidAuthenticationException.class, () -> authorizationService.login(loginRequest));

    assertEquals(AuthenticationMessages.INVALID_AUTHENTICATION_CREDENTIALS, exception.getMessage());
  }

  @Test
  void shouldThrowExceptionIfUserDoesNotExist() {
    when(userRepository.findByLogin(loginRequest.getLogin())).thenReturn(Optional.empty());

    InvalidAuthenticationException exception =
        assertThrows(
            InvalidAuthenticationException.class, () -> authorizationService.login(loginRequest));

    assertEquals(AuthenticationMessages.INVALID_AUTHENTICATION_CREDENTIALS, exception.getMessage());
  }
}
