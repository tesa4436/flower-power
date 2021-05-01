package com.flowerpower.controllers;

import com.flowerpower.data.RegistrationForm;
import com.flowerpower.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
public class UsersController {

    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Autowired
    public UsersController( UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepository = userRepo;
        this.encoder = encoder;
    }

    @RequestMapping(value = "/currentuser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String currentUser(Principal principal) {
        return principal != null && principal.getName() != null ? principal.getName() : "anonymous";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerNewUser(@RequestBody RegistrationForm form) {

        if (form.getUsername() == null || userRepository.findByUsername(form.getUsername()).isPresent()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            validate(form);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        userRepository.save(form.toUser(encoder));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    private void validate(RegistrationForm form) {
        if (form.getPassword() == null || form.getPassword().equals("")) {
            throw new IllegalArgumentException("No password specified");
        }

        if (form.getAddress() == null || form.getAddress().equals("")) {
            throw new IllegalArgumentException("No address specified");
        }

        if (form.getFullName() == null || form.getFullName().equals("")) {
            throw new IllegalArgumentException("No full name specified");
        }

        if (form.getPhoneNumber() == null || form.getPhoneNumber().equals("")) {
            throw new IllegalArgumentException("No phone number specified");
        }
    }
}
