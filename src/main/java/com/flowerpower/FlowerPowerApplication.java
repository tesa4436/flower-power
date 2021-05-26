package com.flowerpower;

import com.flowerpower.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class FlowerPowerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowerPowerApplication.class, args);
	}

}
