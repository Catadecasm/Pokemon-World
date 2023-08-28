package com.example.pokemondemo.repository;


import com.example.pokemondemo.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FollowRepository extends JpaRepository<Follow, Integer> {
}
