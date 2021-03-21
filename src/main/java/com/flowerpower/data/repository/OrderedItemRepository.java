package com.flowerpower.data.repository;

import com.flowerpower.data.model.OrderedItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderedItemRepository extends CrudRepository<OrderedItem, Long> {
}
