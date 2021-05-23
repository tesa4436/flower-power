package com.flowerpower.controllers;

import com.flowerpower.data.model.Basket;
import com.flowerpower.data.model.Item;
import com.flowerpower.data.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class BasketsController {

    private BasketRepository basketRepository;
    private Authentication auth;

    @Autowired
    public BasketsController(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
        this.auth = SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping(value = "/baskets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Basket> getBaskets() {

        Iterable<Basket> allBaskets = basketRepository.findAll();

        if (auth != null && !auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            allBaskets.forEach(bsk -> bsk.getItems().forEach(it -> it.setAmount(null)));
        }

        return allBaskets;
    }

    @RequestMapping(value = "/basket/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Basket> getBasketById(@PathVariable("id") Long id) {

        Optional<Basket> basketOpt = basketRepository.findById(id);

        if (!basketOpt.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Basket basket = basketOpt.get();

        if (auth != null && !auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            basket.getItems().forEach(it -> it.setAmount(null));
        }

        return new ResponseEntity<>(basket, HttpStatus.OK);
    }
}