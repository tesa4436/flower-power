package com.flowerpower.controllers;

import com.flowerpower.data.RegistrationForm;
import com.flowerpower.data.repository.UserRepository;
import com.flowerpower.security.User;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/user/{name}/exists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean userExists(@PathVariable String name) {
        return userRepository.findByUsername(name).isPresent();
    }

    @RequestMapping(value = "/user/exists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> userWithPassExists(@RequestBody User user) {

        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }

        var existingUser = userRepository.findByUsername(user.getUsername());

        return existingUser.isPresent() ? new ResponseEntity<>(true, HttpStatus.OK) :
                new ResponseEntity<>(false, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerNewUser(@RequestBody RegistrationForm form) {

        if (form.getUsername() == null || userRepository.findByUsername(form.getUsername()).isPresent()) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
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
        if (Strings.isNullOrEmpty(form.getPassword())) {
            throw new IllegalArgumentException("No password specified");
        }

        if (Strings.isNullOrEmpty(form.getAddress())) {
            throw new IllegalArgumentException("No address specified");
        }

        if (Strings.isNullOrEmpty(form.getFullName())) {
            throw new IllegalArgumentException("No full name specified");
        }

        if (Strings.isNullOrEmpty(form.getPhoneNumber())) {
            throw new IllegalArgumentException("No phone number specified");
        }
    }
}
