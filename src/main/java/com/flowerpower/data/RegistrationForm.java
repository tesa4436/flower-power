package com.flowerpower.data;

import com.flowerpower.security.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullName;
    private String address;
    private String phoneNumber;

    public User toUser(PasswordEncoder encoder) {
        return User.builder()
                .username(username)
                .password(encoder.encode(password))
                .fullName(fullName)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
    }
}
