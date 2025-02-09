package com.grz55.useritems.item;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.grz55.useritems.dto.ItemDTO;
import com.grz55.useritems.entity.ItemEntity;
import com.grz55.useritems.entity.UserEntity;
import com.grz55.useritems.exception.InvalidAuthenticationException;
import com.grz55.useritems.exception.NotFoundException;
import com.grz55.useritems.message.AuthenticationMessages;
import com.grz55.useritems.message.UserMessages;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class ItemGetAllTest extends ItemServiceBaseTest {

  private List<ItemEntity> itemEntities;
  private List<ItemDTO> itemDTOs;
  private UserEntity userEntity;

  @BeforeEach
  void setUp() {
    userEntity = new UserEntity();
    userEntity.setId(UUID.randomUUID());
    userEntity.setLogin(USER_DEFAULT_LOGIN);

    ItemEntity item1 = new ItemEntity("Item 1", userEntity);
    ItemEntity item2 = new ItemEntity("Item 2", userEntity);
    itemEntities = List.of(item1, item2);

    ItemDTO itemDTO1 = new ItemDTO();
    itemDTO1.setTitle(item1.getName());
    ItemDTO itemDTO2 = new ItemDTO();
    itemDTO2.setTitle(item2.getName());
    itemDTOs = List.of(itemDTO1, itemDTO2);
  }

  @Test
  void shouldReturnUserItemsSuccessfully() {
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    SecurityContextHolder.setContext(securityContext);

    when(authentication.getName()).thenReturn(USER_DEFAULT_LOGIN);
    when(securityContext.getAuthentication()).thenReturn(authentication);

    when(userRepository.findByLogin(USER_DEFAULT_LOGIN)).thenReturn(Optional.of(userEntity));
    when(itemRepository.findByOwnerId(userEntity.getId())).thenReturn(itemEntities);

    List<ItemDTO> result = itemService.getUserItems();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(itemEntities.get(0).getName(), result.get(0).getTitle());
    assertEquals(itemEntities.get(1).getName(), result.get(1).getTitle());
  }

  @Test
  void shouldThrowExceptionIfUserNotAuthenticated() {
    SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());

    InvalidAuthenticationException exception =
        assertThrows(InvalidAuthenticationException.class, () -> itemService.getUserItems());

    assertEquals(
        AuthenticationMessages.AUTHENTICATION_IS_MISSING_OR_INVALID, exception.getMessage());
    verifyNoInteractions(itemRepository);
  }

  @Test
  void shouldThrowExceptionIfUserNotFound() {
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    SecurityContextHolder.setContext(securityContext);

    when(authentication.getName()).thenReturn(USER_UNKNOWN_LOGIN);
    when(securityContext.getAuthentication()).thenReturn(authentication);

    when(userRepository.findByLogin(USER_UNKNOWN_LOGIN)).thenReturn(Optional.empty());

    NotFoundException exception =
        assertThrows(NotFoundException.class, () -> itemService.getUserItems());

    assertEquals(
        UserMessages.USER_NOT_FOUND_BY_USERNAME + USER_UNKNOWN_LOGIN, exception.getMessage());
    verifyNoInteractions(itemRepository);
  }
}
