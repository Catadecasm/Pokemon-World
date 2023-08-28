package com.example.pokemondemo.repository;


import com.example.pokemondemo.domain.Medals;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MedalsRepository extends JpaRepository<Medals, Integer> {

    boolean existsByTitleIgnoreCase(String title);

}
