package com.grz55.useritems.service.impl;

import com.grz55.useritems.dto.ItemCreateRequestDTO;
import com.grz55.useritems.dto.ItemDTO;
import com.grz55.useritems.entity.ItemEntity;
import com.grz55.useritems.entity.UserEntity;
import com.grz55.useritems.exception.InvalidAuthenticationException;
import com.grz55.useritems.exception.NotFoundException;
import com.grz55.useritems.mapper.ItemMapper;
import com.grz55.useritems.message.AuthenticationMessages;
import com.grz55.useritems.message.UserMessages;
import com.grz55.useritems.repository.ItemRepository;
import com.grz55.useritems.repository.UserRepository;
import com.grz55.useritems.service.IItemService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements IItemService {

  private final ItemRepository itemRepository;
  private final UserRepository userRepository;
  private final ItemMapper itemMapper;

  @Override
  public void createItem(ItemCreateRequestDTO itemCreateRequest) {
    String username = getAuthenticatedUsername();
    UserEntity user = fetchUserByLogin(username);

    ItemEntity item = new ItemEntity(itemCreateRequest.getTitle(), user);
    itemRepository.save(item);
  }

  @Override
  public List<ItemDTO> getUserItems() {
    String username = getAuthenticatedUsername();
    UserEntity user = fetchUserByLogin(username);

    List<ItemEntity> items = itemRepository.findByOwnerId(user.getId());
    return itemMapper.toItemDTOList(items);
  }

  private String getAuthenticatedUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || authentication.getName() == null) {
      throw new InvalidAuthenticationException(
          AuthenticationMessages.AUTHENTICATION_IS_MISSING_OR_INVALID);
    }
    return authentication.getName();
  }

  private UserEntity fetchUserByLogin(String username) {
    return userRepository
        .findByLogin(username)
        .orElseThrow(
            () -> new NotFoundException(UserMessages.USER_NOT_FOUND_BY_USERNAME + username));
  }
}
