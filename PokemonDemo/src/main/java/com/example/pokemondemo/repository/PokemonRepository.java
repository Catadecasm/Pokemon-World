package com.example.pokemondemo.repository;


import com.example.pokemondemo.domain.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

}
