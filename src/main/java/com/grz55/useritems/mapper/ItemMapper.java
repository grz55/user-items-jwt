package com.grz55.useritems.mapper;

import com.grz55.useritems.dto.ItemDTO;
import com.grz55.useritems.entity.ItemEntity;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

  public List<ItemDTO> toItemDTOList(List<ItemEntity> items) {
    return items.stream().map(this::toItemDTO).toList();
  }

  public ItemDTO toItemDTO(ItemEntity item) {
    return new ItemDTO(item.getId(), item.getName());
  }
}
