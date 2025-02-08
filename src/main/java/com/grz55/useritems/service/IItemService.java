package com.grz55.useritems.service;

import com.grz55.useritems.dto.ItemCreateRequestDTO;
import com.grz55.useritems.dto.ItemDTO;
import java.util.List;

public interface IItemService {

  void createItem(ItemCreateRequestDTO itemCreateRequest);

  List<ItemDTO> getUserItems();
}
