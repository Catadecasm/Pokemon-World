package com.example.pokemondemo.repository;


import com.example.pokemondemo.domain.Mechanism;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MechanismRepository extends JpaRepository<Mechanism, Integer> {
}
