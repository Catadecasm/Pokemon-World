package com.example.pokemondemo.repository;


import com.example.pokemondemo.domain.Follow;
import com.example.pokemondemo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FollowRepository extends JpaRepository<Follow, Integer> {

    Follow findFollowByFollowedAndFollower(User followed, User follower);
    Integer countFollowByFollowed(User followed);
    Integer countFollowByFollower(User follower);
}
