package com.flowerpower.controllers;

import com.flowerpower.data.model.Item;
import com.flowerpower.data.model.ItemPhoto;
import com.flowerpower.data.repository.ItemRepository;
import com.flowerpower.data.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ItemsController {

    private ItemRepository itemRepository;
    private PhotoRepository photoRepository;

    @Autowired
    public ItemsController(ItemRepository itemRepository, PhotoRepository photoRepository) {
        this.itemRepository = itemRepository;
        this.photoRepository = photoRepository;
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Item> getItems() {
        return itemRepository.findAll();
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> getItemById(@PathVariable("id") Long id) {

        Optional<Item> item = itemRepository.findById(id);

        if (item.isPresent()) {
            return new ResponseEntity<>(item.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/photo/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> getItemPhoto(@PathVariable("id") Long id) {

        Optional<ItemPhoto> photo = photoRepository.findById(id);

        if (photo.isPresent()) {
            return new ResponseEntity<>(photo.get().getPhoto(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> ping() {
        return Collections.singletonMap("response", "pong");
    }
}