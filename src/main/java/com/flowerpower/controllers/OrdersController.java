package com.flowerpower.controllers;

import com.flowerpower.data.model.Order;
import com.flowerpower.data.model.OrderStatus;
import com.flowerpower.data.repository.OrderRepository;
import com.flowerpower.data.repository.UserRepository;
import com.flowerpower.order.OrderProcessor;
import com.flowerpower.order.OutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.OptimisticLockException;
import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class OrdersController {

    private OrderRepository orderRepository;
    private OrderProcessor orderProcessor;
    private UserRepository userRepository;

    @Autowired
    public OrdersController(OrderRepository orderRepository, OrderProcessor orderProcessor, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderProcessor = orderProcessor;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Order> getOrders(Principal principal) {
        if(principal==null){
            return null;
        }
        var curentUser = userRepository.findByUsername(principal.getName());

        if (curentUser != null && curentUser.getRole().equals("ADMIN")) {
            return orderRepository.findAll();
        }

        return orderRepository.findByUserId(curentUser.getId());
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {

        Optional<Order> order = orderRepository.findById(id);

        if (order.isPresent()) {
            return new ResponseEntity<>(order.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> placeOrder(@RequestBody Order order, Principal principal) {
        try {
            order.setStatus(OrderStatus.IN_PROGRESS);
            orderProcessor.place(order, principal);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (OptimisticLockException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/order", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateOrder(@RequestBody Order order) {
        try {
            orderProcessor.update(order);
            return new ResponseEntity<>(null, HttpStatus.CREATED);

        } catch (OutOfStockException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (OptimisticLockException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}