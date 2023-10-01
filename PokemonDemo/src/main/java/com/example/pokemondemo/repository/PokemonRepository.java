package com.example.pokemondemo.repository;


import com.example.pokemondemo.entity.Pokemon;
import com.example.pokemondemo.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    List<Pokemon> findAllByUser(User user, Pageable pageable);
    List<Pokemon> findAllByUser(User user);
    Integer countAllByUser(User user);
    boolean existsByIdAndUser(int id, User user);

}
