package com.example.pokemondemo.repository;


import com.example.pokemondemo.domain.Mechanism;
import com.example.pokemondemo.domain.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MechanismRepository extends JpaRepository<Mechanism, Integer> {
    boolean findByNameIgnoreCase(String name);
    boolean findByNameIgnoreCaseAAndPokemonid(String name, Pokemon pokemon);
}
