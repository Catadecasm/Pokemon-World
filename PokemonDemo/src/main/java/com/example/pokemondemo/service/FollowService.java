package com.example.pokemondemo.service;


import java.util.List;

import com.example.pokemondemo.domain.User;
import com.example.pokemondemo.util.*;
import com.example.pokemondemo.domain.Follow;
import com.example.pokemondemo.model.DataBase.FollowDTO;
import com.example.pokemondemo.repository.FollowRepository;
import com.example.pokemondemo.repository.UserRepository;
import org.springframework.data.domain.Sort;
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

    public List<FollowDTO> findAll() {
        final List<Follow> follows = followRepository.findAll(Sort.by("id"));
        return follows.stream()
                .map(follow -> mapToDTO(follow, new FollowDTO()))
                .toList();
    }

    public FollowDTO get(final Integer id) {
        return followRepository.findById(id)
                .map(follow -> mapToDTO(follow, new FollowDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final FollowDTO followDTO) {
        final Follow follow = new Follow();
        mapToEntity(followDTO, follow);
        return followRepository.save(follow).getId();
    }

    public void update(final Integer id, final FollowDTO followDTO) {
        final Follow follow = followRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(followDTO, follow);
        followRepository.save(follow);
    }

    public void delete(final Integer id) {
        followRepository.deleteById(id);
    }

    private FollowDTO mapToDTO(final Follow follow, final FollowDTO followDTO) {
        followDTO.setId(follow.getId());
        followDTO.setFollowed(follow.getFollowed() == null ? null : follow.getFollowed().getId());
        followDTO.setFollower(follow.getFollower() == null ? null : follow.getFollower().getId());
        return followDTO;
    }

    private Follow mapToEntity(final FollowDTO followDTO, final Follow follow) {
        final User followed = followDTO.getFollowed() == null ? null : userRepository.findById(followDTO.getFollowed())
                .orElseThrow(() -> new NotFoundException("followed not found"));
        follow.setFollowed(followed);
        final User follower = followDTO.getFollower() == null ? null : userRepository.findById(followDTO.getFollower())
                .orElseThrow(() -> new NotFoundException("follower not found"));
        follow.setFollower(follower);
        return follow;
    }

}
