package com.example.pokemondemo.repository;


import com.example.pokemondemo.domain.Pokemon;
import com.example.pokemondemo.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TypeRepository extends JpaRepository<Type, Integer> {
    boolean findByNameIgnoreCase(String name);
    boolean findByNameIgnoreCaseAndPokemonid(String name, Pokemon pokemon);
}
