package com.flowerpower.controllers;

import com.flowerpower.data.model.OrderedItem;
import com.flowerpower.data.repository.OrderedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class OrderedItemsController {

    private OrderedItemRepository orderedItemRepository;

    @Autowired
    public OrderedItemsController(OrderedItemRepository orderedItemRepository) {
        this.orderedItemRepository = orderedItemRepository;
    }

    @RequestMapping(value = "/ordered_items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<OrderedItem> getOrderedItems() {
        return orderedItemRepository.findAll();
    }

    @RequestMapping(value = "/ordered_item/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderedItem> getOrderedItemById(@PathVariable("id") Long id) {

        Optional<OrderedItem> item = orderedItemRepository.findById(id);

        if (item.isPresent()) {
            return new ResponseEntity<>(item.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}