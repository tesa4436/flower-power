package com.flowerpower.order;

import com.flowerpower.data.model.Item;
import com.flowerpower.data.model.Order;
import com.flowerpower.data.repository.ItemRepository;
import com.flowerpower.data.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderProcessor {

    private ItemRepository itemRepository;
    private OrderRepository orderRepository;

    @Autowired
    public OrderProcessor(ItemRepository itemRepository, OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }

    public void place(Order order) throws OutOfStockException {
        validate(order);
        orderRepository.save(order);
    }

    public void update(Order order) throws OutOfStockException {

        validate(order);

        Optional<Order> oldOrderOptional = orderRepository.findById(order.getOrderId());
        Order oldOrder;

        if (!oldOrderOptional.isPresent()) {
            throw new IllegalArgumentException("The order requested to be updated does not exist");
        }

        oldOrder = oldOrderOptional.get();
        oldOrder.setGreetingMessage(order.getGreetingMessage());
        oldOrder.setShippingAddress(order.getShippingAddress());
        oldOrder.setBasket(order.getBasket());

        orderRepository.save(oldOrder);
    }

    private void validate(Order order) throws OutOfStockException {
        if (order == null) {
            throw new IllegalArgumentException("No order present");
        }

        if (order.getBasket() == null || order.getBasket().getItems().isEmpty()) {
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
