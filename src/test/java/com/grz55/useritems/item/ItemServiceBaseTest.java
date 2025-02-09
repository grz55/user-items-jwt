package com.grz55.useritems.item;

import com.grz55.useritems.mapper.ItemMapper;
import com.grz55.useritems.repository.ItemRepository;
import com.grz55.useritems.repository.UserRepository;
import com.grz55.useritems.service.IItemService;
import com.grz55.useritems.service.impl.ItemServiceImpl;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

abstract class ItemServiceBaseTest {

  protected static final String USER_UNKNOWN_LOGIN = "unknownUser";
  protected static final String USER_DEFAULT_LOGIN = "login";

  @MockitoBean protected ItemRepository itemRepository = Mockito.mock(ItemRepository.class);

  @MockitoBean protected UserRepository userRepository = Mockito.mock(UserRepository.class);

  protected ItemMapper itemMapper = new ItemMapper();

  protected IItemService itemService =
      new ItemServiceImpl(itemRepository, userRepository, itemMapper);
}
