package com.example.pokemondemo.repository;


import com.example.pokemondemo.entity.Mechanism;
import com.example.pokemondemo.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MechanismRepository extends JpaRepository<Mechanism, Integer> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndPokemonid(String name, Pokemon pokemon);
}
