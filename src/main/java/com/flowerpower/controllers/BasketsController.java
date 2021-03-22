package com.flowerpower.controllers;

import com.flowerpower.data.model.Basket;
import com.flowerpower.data.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class BasketsController {

    private BasketRepository basketRepository;

    @Autowired
    public BasketsController(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @RequestMapping(value = "/baskets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Basket> getBaskets() {
        return basketRepository.findAll();
    }

    @RequestMapping(value = "/basket/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Basket> getBasketById(@PathVariable("id") Long id) {

        Optional<Basket> basket = basketRepository.findById(id);

        if (basket.isPresent()) {
            return new ResponseEntity<>(basket.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}