package com.flowerpower.order;

import com.flowerpower.data.model.Item;
import com.flowerpower.data.model.Order;
import com.flowerpower.data.repository.ItemRepository;
import com.flowerpower.data.repository.OrderRepository;
import com.flowerpower.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;

@Component
public class OrderProcessor {

    private ItemRepository itemRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    @Autowired
    public OrderProcessor(ItemRepository itemRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public void place(Order order, Principal principal) throws OutOfStockException {

        validateAndProcess(order);

        if (principal != null) {
            var orderingUser = userRepository.findByUsername(principal.getName());
            order.setUserId(orderingUser.getId());
        }

        orderRepository.save(order);
    }

    public void update(Order order) throws OutOfStockException {

        validateAndProcess(order);

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

    private void validateAndProcess(Order order) throws OutOfStockException {
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

            Long newAmount = dbItem.get().getAmount() - 1 <= 0 ? 0 : dbItem.get().getAmount() - 1;
            dbItem.get().setAmount(newAmount);
        }
    }
}
