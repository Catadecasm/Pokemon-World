package com.example.pokemondemo.service.app;


import com.example.pokemondemo.domain.Fight;
import com.example.pokemondemo.domain.User;
import com.example.pokemondemo.model.payload.request.OpponentDTO;
import com.example.pokemondemo.model.payload.response.FightResponseDTO;
import com.example.pokemondemo.repository.FightRepository;
import com.example.pokemondemo.repository.UserRepository;
import com.example.pokemondemo.util.*;
import com.example.pokemondemo.model.payload.request.FightDTO;
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



    public void delete(final Integer id) {
        fightRepository.deleteById(id);
    }


    public FightResponseDTO createFight(String userEmail, FightDTO fightDTO) {
        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        if(!userRepository.existsByUsernameIgnoreCase(fightDTO.getOpponent_1().getUsername()) || !userRepository.existsByUsernameIgnoreCase(fightDTO.getOpponent_2().getUsername())){
            throw new NotFoundException("One or both of the given trainers does not exist");
        }
        if(!user.getUsername().equals(fightDTO.getOpponent_1().getUsername()) && !user.getUsername().equals(fightDTO.getOpponent_2().getUsername())){
            throw new NotFoundException("The given fight is not a fight made by you");
        }
        if(user.getUsername().equals(fightDTO.getOpponent_1().getUsername()) && user.getUsername().equals(fightDTO.getOpponent_2().getUsername())){
            throw new NotFoundException("You can't fight yourself");
        }

        if(user.getUsername().equals(fightDTO.getOpponent_1().getUsername())){
            User you = userRepository.findByUsernameIgnoreCase(fightDTO.getOpponent_1().getUsername());
            User opponent = userRepository.findByUsernameIgnoreCase(fightDTO.getOpponent_2().getUsername());
            if(!you.getLeagueid().equals(opponent.getLeagueid())){
                throw new NotFoundException("You can't fight a trainer from a different league");
            }
        }else{

        }
    }
}
