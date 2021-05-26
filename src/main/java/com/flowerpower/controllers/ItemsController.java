package com.flowerpower.controllers;

import com.flowerpower.data.model.Item;
import com.flowerpower.data.model.ItemPhoto;
import com.flowerpower.data.repository.ItemRepository;
import com.flowerpower.data.repository.PhotoRepository;
import com.flowerpower.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ItemsController {

    private ItemRepository itemRepository;
    private PhotoRepository photoRepository;
    private UserRepository userRepository;

    @Autowired
    public ItemsController(ItemRepository itemRepository, PhotoRepository photoRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Item> getItems(Principal principal) {

        Iterable<Item> allItems = itemRepository.findAll();
        var currentUser = principal != null ? userRepository.findByUsername(principal.getName()) : null;

        if (currentUser != null && !currentUser.getRole().equals("ADMIN")) {
            allItems.forEach(item -> item.setAmount(null));
        }

        return allItems;
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> getItemById(@PathVariable("id") Long id, Principal principal) {

        Optional<Item> itemOpt = itemRepository.findById(id);

        if (!itemOpt.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Item item = itemOpt.get();

        var currentUser = principal != null ? userRepository.findByUsername(principal.getName()) : null;

        if (currentUser != null && !currentUser.getRole().equals("ADMIN")) {
            item.setAmount(null);
        }

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @RequestMapping(value = "/photo/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> getItemPhoto(@PathVariable("id") Long id) {

        Optional<ItemPhoto> photo = photoRepository.findById(id);

        if (photo.isPresent()) {
            return new ResponseEntity<>(photo.get().getPhoto(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/item/{itemId}/photo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemPhoto> getItemPhotoByItemId(@PathVariable Long itemId) {
        Optional<Item> itemOpt = itemRepository.findById(itemId);

        if (!itemOpt.isPresent() || itemOpt.get().getPhotoId() == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Item item = itemOpt.get();
        Optional<ItemPhoto> photo = photoRepository.findById(item.getPhotoId());

        return photo.isPresent() ? new ResponseEntity<>(photo.get(), HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/item/{itemId}/photo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadItemPhoto(@RequestBody byte[] photo, @PathVariable Long itemId) {
        Optional<Item> itemOpt = itemRepository.findById(itemId);

        if (!itemOpt.isPresent() || photo == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        Item item = itemOpt.get();
        ItemPhoto uploadedPhoto = ItemPhoto.builder().photo(photo).build();
        Long photoId = photoRepository.save(uploadedPhoto).getId();
        item.setPhotoId(photoId);
        itemRepository.save(item);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> ping() {
        return Collections.singletonMap("response", "pong");
    }
}