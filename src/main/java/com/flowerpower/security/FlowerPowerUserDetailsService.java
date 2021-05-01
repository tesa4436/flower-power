package com.flowerpower.security;

import com.flowerpower.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlowerPowerUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public FlowerPowerUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(userName);

        if (user.isPresent()) {
            return user.get();
        }

        throw new UsernameNotFoundException("User with name " + userName + " not found");
    }
}
