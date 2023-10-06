package com.example.pokemondemo.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import com.example.pokemondemo.entity.Fight;
import com.example.pokemondemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FightRepository extends JpaRepository<Fight, Integer> {

    //List<Fight> findAllByUserFightUsers(User user);
    List<Fight> findAllByUserFightUsers(User userFightUsers, Pageable pageable);
    Integer countAllByUserId(Integer user);
    Integer countAllByUserIdAndWinnerName(Integer user, String name);
    boolean existsById(Integer id);

}
