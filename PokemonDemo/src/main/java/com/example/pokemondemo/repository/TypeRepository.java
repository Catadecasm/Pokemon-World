package com.example.pokemondemo.repository;


import com.example.pokemondemo.domain.Pokemon;
import com.example.pokemondemo.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TypeRepository extends JpaRepository<Type, Integer> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndPokemonid(String name, Pokemon pokemon);
    List<Type> findAllByPokemonid(Pokemon pokemon);
}
