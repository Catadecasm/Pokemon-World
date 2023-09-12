package com.example.pokemondemo.service;


import com.example.pokemondemo.entity.Fight;
import com.example.pokemondemo.entity.User;
import com.example.pokemondemo.model.dto.OpponentDTO;
import com.example.pokemondemo.model.dto.FightListDTO;
import com.example.pokemondemo.model.dto.FightResponseDTO;
import com.example.pokemondemo.model.dto.FightResultDTO;
import com.example.pokemondemo.model.dto.ResultDTO;
import com.example.pokemondemo.repository.FightRepository;
import com.example.pokemondemo.repository.FollowRepository;
import com.example.pokemondemo.repository.LeagueRepository;
import com.example.pokemondemo.repository.UserRepository;
import com.example.pokemondemo.exception.*;
import com.example.pokemondemo.model.dto.FightDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
@Transactional
public class FightService {

    private final FightRepository fightRepository;
    private final UserRepository userRepository;
    private final LeagueRepository leagueRepository;
    private final FollowRepository followRepository;

    public FightService(final FightRepository fightRepository,
                        final UserRepository userRepository, LeagueRepository leagueRepository, FollowRepository followRepository) {
        this.fightRepository = fightRepository;
        this.userRepository = userRepository;
        this.leagueRepository = leagueRepository;
        this.followRepository = followRepository;
    }



    public void delete(final Integer id) {
        fightRepository.deleteById(id);
    }


    public FightResponseDTO createFight(String userEmail, FightDTO fightDTO) {
        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        if(!userRepository.existsByUsernameIgnoreCase(fightDTO.getOpponent_1().getUsername()) || !userRepository.existsByUsernameIgnoreCase(fightDTO.getOpponent_2().getUsername())){
            throw new NotFoundException("One or both of the given trainers does not exist");
        }
        if(!user.getRealUsername().equals(fightDTO.getOpponent_1().getUsername()) && !user.getRealUsername().equals(fightDTO.getOpponent_2().getUsername())){
            throw new NotFoundException("The given fight is not a fight made by you");
        }
        if(user.getRealUsername().equals(fightDTO.getOpponent_1().getUsername()) && user.getRealUsername().equals(fightDTO.getOpponent_2().getUsername())){
            throw new NotFoundException("You can't fight yourself");
        }
        if(!leagueRepository.existsByNameIgnoreCase(fightDTO.getLeague())){
            throw new NotFoundException("The given league does not exist");
        }

        if(user.getRealUsername().equals(fightDTO.getOpponent_1().getUsername())){
            User you = userRepository.findByUsernameIgnoreCase(fightDTO.getOpponent_1().getUsername());
            User opponent = userRepository.findByUsernameIgnoreCase(fightDTO.getOpponent_2().getUsername());
            if(!you.getLeagueid().equals(opponent.getLeagueid())){
                throw new NotFoundException("You can't fight a trainer from a different league");
            }
            HashSet<User> users = new HashSet<User>();
            users.add(you);
            users.add(opponent);
            String winner = fightDTO.getOpponent_1().getWinned_rounds() > fightDTO.getOpponent_2().getWinned_rounds() ? fightDTO.getOpponent_1().getUsername() : fightDTO.getOpponent_2().getUsername();
            Fight fight = Fight.builder()
                    .league(fightDTO.getLeague())
                    .creation(LocalDate.now())
                    .updatee(LocalDate.now())
                    .userFightUsers(users)
                    .userId(you.getId())
                    .oponentId(opponent.getId())
                    .winnerName(winner)
                    .rounds(fightDTO.getRounds())
                    .build();
            Integer id = fightRepository.save(fight).getId();
            return FightResponseDTO.builder()
                    .id(id)
                    .createAt(fight.getCreation().toString())
                    .updateAt(fight.getUpdatee().toString())
                    .build();

        }else{
            User you = userRepository.findByUsernameIgnoreCase(fightDTO.getOpponent_2().getUsername());
            User opponent = userRepository.findByUsernameIgnoreCase(fightDTO.getOpponent_1().getUsername());
            if(!you.getLeagueid().equals(opponent.getLeagueid())){
                throw new NotFoundException("You can't fight a trainer from a different league");
            }
            HashSet<User> users = new HashSet<User>();
            users.add(you);
            users.add(opponent);
            String winner = fightDTO.getOpponent_1().getWinned_rounds() > fightDTO.getOpponent_2().getWinned_rounds() ? fightDTO.getOpponent_1().getUsername() : fightDTO.getOpponent_2().getUsername();
            Fight fight = Fight.builder()
                    .league(fightDTO.getLeague())
                    .creation(LocalDate.now())
                    .updatee(LocalDate.now())
                    .userFightUsers(users)
                    .userId(you.getId())
                    .oponentId(opponent.getId())
                    .winnerName(winner)
                    .rounds(fightDTO.getRounds())
                    .build();
            fightRepository.save(fight);
            Integer id = fightRepository.save(fight).getId();
            return FightResponseDTO.builder()
                    .id(id)
                    .createAt(fight.getCreation().toString())
                    .updateAt(fight.getUpdatee().toString())
                    .build();

        }
    }

    public FightListDTO getFight(String username, Integer quantity, Integer offset, String userEmail) {
        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        User search = userRepository.findByUsernameIgnoreCase(username);
        if(!userRepository.existsByUsernameIgnoreCase(username)){
            throw new NotFoundException("The given trainer does not exist");
        }
        if(!user.getRealUsername().equals(username)){
            if(followRepository.findFollowByFollowedAndFollower(search, user) == null){
                throw new NotFoundException("You are not following the given trainer");
            }else{
                Pageable pageable = PageRequest.of(offset, quantity);
                List<Fight> fights = fightRepository.findAllByUserFightUsers(search, pageable);
                FightListDTO fightListDTO =  new FightListDTO();
                fightListDTO.setIndex(offset);
                fightListDTO.setQuantity(quantity);
                fightListDTO.setResult(new ArrayList<>());
                for(Fight fight : fights){
                    FightResultDTO resultDTO = new FightResultDTO();
                    resultDTO.setId(fight.getId());
                    resultDTO.setCreateAt(fight.getCreation().toString());
                    resultDTO.setUpdateAt(fight.getUpdatee().toString());
                    resultDTO.setLeague(fight.getLeague());
                    resultDTO.setResult(ResultDTO.builder()
                                    .winner(fight.getWinnerName())
                                    .rounds(fight.getRounds()).build());
                    resultDTO.setOpponent(OpponentDTO.builder()
                            .email(search.getEmail())
                            .username(search.getRealUsername())
                            .id(search.getId())
                            .build());
                    fightListDTO.getResult().add(resultDTO);
                }
                return fightListDTO;
            }
        }else{
            Pageable pageable = PageRequest.of(offset, quantity);
            List<Fight> fights = fightRepository.findAllByUserFightUsers(user, pageable);
            FightListDTO fightListDTO =  new FightListDTO();
            fightListDTO.setIndex(offset);
            fightListDTO.setQuantity(quantity);
            fightListDTO.setResult(new ArrayList<>());
            for(Fight fight : fights){
                FightResultDTO resultDTO = new FightResultDTO();
                resultDTO.setId(fight.getId());
                resultDTO.setCreateAt(fight.getCreation().toString());
                resultDTO.setUpdateAt(fight.getUpdatee().toString());
                resultDTO.setLeague(fight.getLeague());
                resultDTO.setResult(ResultDTO.builder()
                        .winner(fight.getWinnerName())
                        .rounds(fight.getRounds()).build());
                resultDTO.setOpponent(OpponentDTO.builder()
                        .email(user.getEmail())
                        .username(user.getRealUsername())
                        .id(user.getId())
                        .build());
                fightListDTO.getResult().add(resultDTO);
            }
            return fightListDTO;
        }
    }
}
