package com.example.pokemondemo.repository;

import com.example.pokemondemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByEmailIgnoreCase(String email);

    User findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);

}
