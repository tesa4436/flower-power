package com.flowerpower.data.repository;

import com.flowerpower.data.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query("from Order u where u.userId in ?1")
    Iterable<Order> findByUserId(Long id);
}
