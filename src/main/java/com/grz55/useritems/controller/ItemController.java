package com.grz55.useritems.controller;

import com.grz55.useritems.dto.ItemCreateRequestDTO;
import com.grz55.useritems.dto.ItemDTO;
import com.grz55.useritems.service.IItemService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

  private final IItemService itemService;

  @PostMapping
  public ResponseEntity<Void> createItem(
      @Valid @RequestBody ItemCreateRequestDTO itemCreateRequest) {
    itemService.createItem(itemCreateRequest);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping
  public ResponseEntity<List<ItemDTO>> getUserItems() {
    List<ItemDTO> userItems = itemService.getUserItems();
    return ResponseEntity.ok(userItems);
  }
}
