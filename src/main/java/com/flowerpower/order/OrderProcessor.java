package com.flowerpower.order;

import com.flowerpower.data.model.Item;
import com.flowerpower.data.model.Order;
import com.flowerpower.data.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderProcessor {

    private ItemRepository itemRepository;

    @Autowired
    public OrderProcessor(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void placeOrder(Order order) throws OutOfStockException {
        validateOrder(order);
    }

    private void validateOrder(Order order) throws OutOfStockException {
        if (order == null) {
            throw new IllegalArgumentException("No order present");
        }

        if (order.getBasket() == null) {
            throw new IllegalArgumentException("The order has no associated items");
        }

        for (Item it : order.getBasket().getItems()) {
            if (it.getId() == null) {
                throw new IllegalArgumentException("No item id in basket");
            }

            Optional<Item> dbItem = itemRepository.findById(it.getId());

            if (!dbItem.isPresent()) {
                throw new IllegalArgumentException("Item with id " + it.getId() + " does not exist");
            }

            if (dbItem.get().getAmount() <= 0) {
                throw new OutOfStockException("The item with id " + it.getId() + " is out of stock");
            }
        }
    }
}
