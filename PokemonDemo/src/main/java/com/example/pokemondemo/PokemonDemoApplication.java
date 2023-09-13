package com.example.pokemondemo;

import com.example.pokemondemo.entity.User;
import com.example.pokemondemo.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class PokemonDemoApplication {

    private static UserRepository userRepository;

    public PokemonDemoApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PokemonDemoApplication.class, args);
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setLogged(false);
            userRepository.save(user);
        }
    }

}
