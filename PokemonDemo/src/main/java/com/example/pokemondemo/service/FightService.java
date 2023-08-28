package com.example.pokemondemo.service;


import com.example.pokemondemo.domain.Fight;
import com.example.pokemondemo.domain.User;
import com.example.pokemondemo.repository.FightRepository;
import com.example.pokemondemo.repository.UserRepository;
import com.example.pokemondemo.util.*;
import com.example.pokemondemo.model.FightDTO;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class FightService {

    private final FightRepository fightRepository;
    private final UserRepository userRepository;

    public FightService(final FightRepository fightRepository,
            final UserRepository userRepository) {
        this.fightRepository = fightRepository;
        this.userRepository = userRepository;
    }

    public List<FightDTO> findAll() {
        final List<Fight> fights = fightRepository.findAll(Sort.by("id"));
        return fights.stream()
                .map(fight -> mapToDTO(fight, new FightDTO()))
                .toList();
    }

    public FightDTO get(final Integer id) {
        return fightRepository.findById(id)
                .map(fight -> mapToDTO(fight, new FightDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final FightDTO fightDTO) {
        final Fight fight = new Fight();
        mapToEntity(fightDTO, fight);
        return fightRepository.save(fight).getId();
    }

    public void update(final Integer id, final FightDTO fightDTO) {
        final Fight fight = fightRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(fightDTO, fight);
        fightRepository.save(fight);
    }

    public void delete(final Integer id) {
        fightRepository.deleteById(id);
    }

    private FightDTO mapToDTO(final Fight fight, final FightDTO fightDTO) {
        fightDTO.setId(fight.getId());
        fightDTO.setUserId(fight.getUserId());
        fightDTO.setOponentId(fight.getOponentId());
        fightDTO.setRounds(fight.getRounds());
        fightDTO.setCreation(fight.getCreation());
        fightDTO.setUpdatee(fight.getUpdatee());
        fightDTO.setLeague(fight.getLeague());
        fightDTO.setUserFightUsers(fight.getUserFightUsers().stream()
                .map(user -> user.getId())
                .toList());
        return fightDTO;
    }

    private Fight mapToEntity(final FightDTO fightDTO, final Fight fight) {
        fight.setUserId(fightDTO.getUserId());
        fight.setOponentId(fightDTO.getOponentId());
        fight.setRounds(fightDTO.getRounds());
        fight.setCreation(fightDTO.getCreation());
        fight.setUpdatee(fightDTO.getUpdatee());
        fight.setLeague(fightDTO.getLeague());
        final List<User> userFightUsers = userRepository.findAllById(
                fightDTO.getUserFightUsers() == null ? Collections.emptyList() : fightDTO.getUserFightUsers());
        if (userFightUsers.size() != (fightDTO.getUserFightUsers() == null ? 0 : fightDTO.getUserFightUsers().size())) {
            throw new NotFoundException("one of userFightUsers not found");
        }
        fight.setUserFightUsers(userFightUsers.stream().collect(Collectors.toSet()));
        return fight;
    }

}
