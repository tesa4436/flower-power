package com.flowerpower.data.repository;

import com.flowerpower.data.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
