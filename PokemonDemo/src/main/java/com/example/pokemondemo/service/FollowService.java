package com.example.pokemondemo.service;


import java.util.Optional;

import com.example.pokemondemo.entity.User;
import com.example.pokemondemo.model.dto.ClassicResponseDTO;
import com.example.pokemondemo.exception.*;
import com.example.pokemondemo.entity.Follow;
import com.example.pokemondemo.repository.FollowRepository;
import com.example.pokemondemo.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(final FollowRepository followRepository,
                         final UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public ClassicResponseDTO follow(String userEmail, String userToFollow) {

        Optional<User> userFollower = userRepository.findByEmailIgnoreCase(userEmail);
        User userFollowed = userRepository.findByUsernameIgnoreCase(userToFollow);
        if (userFollowed == null) {
            throw new NotFoundException("The trainer does not exist");
        }
        if(userFollowed.getRealUsername().equals(userFollower.get().getRealUsername())){
            throw new NotFoundException("You can't follow yourself");
        }
        if(followRepository.findFollowByFollowedAndFollower(userFollowed, userFollower.get()) != null){
            throw new NotFoundException("You are already following this trainer");
        }

        User user = userFollower.get();
        Follow follow = new Follow();
        follow.setFollower(user);
        follow.setFollowed(userFollowed);
        followRepository.save(follow);
        return ClassicResponseDTO.builder()
                .ResponseCode("OK")
                .ResponseMessage(user.getRealUsername() + " is now following " + userFollowed.getRealUsername())
                .build();
    }

    public ClassicResponseDTO unfollow(String userEmail, String username) {
        Optional<User> userFollower = userRepository.findByEmailIgnoreCase(userEmail);
        User userFollowed = userRepository.findByUsernameIgnoreCase(username);
        if (userFollowed == null) {
            throw new NotFoundException("The trainer does not exist");
        }
        Follow follow = followRepository.findFollowByFollowedAndFollower(userFollowed, userFollower.get());
        if(follow == null){
            throw new NotFoundException("The trainer is not followed by you");
        }
        followRepository.delete(follow);

        return ClassicResponseDTO.builder()
                .ResponseCode("OK")
                .ResponseMessage(userFollower.get().getRealUsername() + " is not following " + userFollowed.getRealUsername() + " anymore")
                .build();
    }

}
