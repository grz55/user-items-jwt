package com.grz55.useritems.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.grz55.useritems.dto.ItemCreateRequestDTO;
import com.grz55.useritems.entity.ItemEntity;
import com.grz55.useritems.entity.UserEntity;
import com.grz55.useritems.exception.InvalidAuthenticationException;
import com.grz55.useritems.exception.NotFoundException;
import com.grz55.useritems.message.AuthenticationMessages;
import com.grz55.useritems.message.UserMessages;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.context.SecurityContextHolder;

class ItemCreateTest extends ItemServiceBaseTest {

  private static final String ITEM_DEFAULT_TITLE = "title";

  private ItemCreateRequestDTO itemCreateRequest;
  private UserEntity userEntity;

  @BeforeEach
  void setUp() {
    itemCreateRequest = new ItemCreateRequestDTO();
    itemCreateRequest.setTitle(ITEM_DEFAULT_TITLE);

    userEntity = new UserEntity();
    userEntity.setLogin(USER_DEFAULT_LOGIN);
  }

  @Test
  void shouldCreateItemSuccessfully() {
    SecurityContextHolder.setContext(securityContext);

    when(authentication.getName()).thenReturn(USER_DEFAULT_LOGIN);
    when(securityContext.getAuthentication()).thenReturn(authentication);

    when(userRepository.findByLogin(USER_DEFAULT_LOGIN)).thenReturn(Optional.of(userEntity));

    itemService.createItem(itemCreateRequest);

    ArgumentCaptor<ItemEntity> itemCaptor = ArgumentCaptor.forClass(ItemEntity.class);
    verify(itemRepository).save(itemCaptor.capture());

    ItemEntity capturedItem = itemCaptor.getValue();
    assertEquals(ITEM_DEFAULT_TITLE, capturedItem.getName());
    assertEquals(USER_DEFAULT_LOGIN, capturedItem.getOwner().getLogin());
  }

  @Test
  void shouldThrowExceptionIfUserNotAuthenticated() {
    SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());

    InvalidAuthenticationException exception =
        assertThrows(
            InvalidAuthenticationException.class, () -> itemService.createItem(itemCreateRequest));

    assertEquals(
        AuthenticationMessages.AUTHENTICATION_IS_MISSING_OR_INVALID, exception.getMessage());
    verifyNoInteractions(itemRepository);
  }

  @Test
  void shouldThrowExceptionIfUserNotFound() {
    SecurityContextHolder.setContext(securityContext);

    when(authentication.getName()).thenReturn(USER_UNKNOWN_LOGIN);
    when(securityContext.getAuthentication()).thenReturn(authentication);

    when(userRepository.findByLogin(USER_UNKNOWN_LOGIN)).thenReturn(Optional.empty());

    NotFoundException exception =
        assertThrows(NotFoundException.class, () -> itemService.createItem(itemCreateRequest));

    assertEquals(
        UserMessages.USER_NOT_FOUND_BY_USERNAME + USER_UNKNOWN_LOGIN, exception.getMessage());
    verifyNoInteractions(itemRepository);
  }
}
