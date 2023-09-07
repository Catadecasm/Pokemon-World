package com.example.pokemondemo.repository;


import com.example.pokemondemo.domain.Pokemon;
import com.example.pokemondemo.domain.Type;
import com.example.pokemondemo.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    List<Pokemon> findAllByUser(User user, Pageable pageable);

}
