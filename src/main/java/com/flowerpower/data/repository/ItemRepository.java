package com.flowerpower.data.repository;

import com.flowerpower.data.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
