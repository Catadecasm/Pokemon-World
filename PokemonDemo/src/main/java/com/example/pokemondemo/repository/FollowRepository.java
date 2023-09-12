package com.example.pokemondemo.repository;


import com.example.pokemondemo.entity.Follow;
import com.example.pokemondemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FollowRepository extends JpaRepository<Follow, Integer> {

    Follow findFollowByFollowedAndFollower(User followed, User follower);
}
