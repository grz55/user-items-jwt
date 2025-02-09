package com.grz55.useritems.authorization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.grz55.useritems.dto.RegistrationRequestDTO;
import com.grz55.useritems.entity.UserEntity;
import com.grz55.useritems.exception.UserAlreadyExistsException;
import com.grz55.useritems.message.UserMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class AuthorizationRegisterTest extends AuthorizationServiceBaseTest {

  private RegistrationRequestDTO registrationRequest;

  @BeforeEach
  void setUp() {
    registrationRequest = new RegistrationRequestDTO();
    registrationRequest.setLogin("user");
    registrationRequest.setPassword("password");
  }

  @Test
  void shouldRegisterIfUserDoesNotExist() {
    String encryptedPassword = "encrypted";
    when(userRepository.existsByLogin(registrationRequest.getLogin())).thenReturn(false);
    when(passwordEncoder.encode(registrationRequest.getPassword())).thenReturn(encryptedPassword);

    authorizationService.register(registrationRequest);

    ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
    verify(userRepository).save(userCaptor.capture());

    UserEntity capturedUser = userCaptor.getValue();
    assertEquals(registrationRequest.getLogin(), capturedUser.getLogin());
    assertEquals(encryptedPassword, capturedUser.getPassword());
  }

  @Test
  void shouldThrowExceptionIfUserAlreadyExists() {
    when(userRepository.existsByLogin(registrationRequest.getLogin())).thenReturn(true);

    UserAlreadyExistsException exception =
        assertThrows(
            UserAlreadyExistsException.class,
            () -> {
              authorizationService.register(registrationRequest);
            });

    assertEquals(
        UserMessages.USER_ALREADY_EXISTS + registrationRequest.getLogin(), exception.getMessage());

    verify(userRepository, never()).save(any(UserEntity.class));
  }
}
