package com.example.pokemondemo.repository;


import com.example.pokemondemo.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LeagueRepository extends JpaRepository<League, Integer> {

    boolean existsByNameIgnoreCase(String name);
    League findByNameIgnoreCase(String name);

}
