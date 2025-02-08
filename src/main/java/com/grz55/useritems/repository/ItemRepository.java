package com.grz55.useritems.repository;

import com.grz55.useritems.entity.ItemEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, UUID> {

  List<ItemEntity> findByOwnerId(UUID ownerId);
}
