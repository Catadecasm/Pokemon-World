package com.example.pokemondemo.User;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository2 extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}
