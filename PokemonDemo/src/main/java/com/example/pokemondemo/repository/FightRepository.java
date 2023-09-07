package com.example.pokemondemo.repository;


import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import com.example.pokemondemo.domain.Fight;
import com.example.pokemondemo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FightRepository extends JpaRepository<Fight, Integer> {

    List<Fight> findAllByUserFightUsers(User user);
    List<Fight> findAllByUserFightUsers(User userFightUsers, Pageable pageable);

}
