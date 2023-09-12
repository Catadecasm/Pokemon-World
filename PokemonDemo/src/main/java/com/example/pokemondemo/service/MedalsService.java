package com.example.pokemondemo.service;


import com.example.pokemondemo.entity.Medals;
import com.example.pokemondemo.entity.User;
import com.example.pokemondemo.model.dto.AddMedalDTO;
import com.example.pokemondemo.model.dto.MedalResponseDTO;
import com.example.pokemondemo.repository.FightRepository;
import com.example.pokemondemo.repository.MedalsRepository;
import com.example.pokemondemo.repository.UserRepository;
import com.example.pokemondemo.exception.*;
import org.springframework.stereotype.Service;


@Service
public class MedalsService {

    private final MedalsRepository medalsRepository;
    private final UserRepository userRepository;
    private final FightRepository fightRepository;

    public MedalsService(final MedalsRepository medalsRepository,
                         final UserRepository userRepository, FightRepository fightRepository) {
        this.medalsRepository = medalsRepository;
        this.userRepository = userRepository;
        this.fightRepository = fightRepository;
    }

    public boolean titleExists(final String title) {
        return medalsRepository.existsByTitleIgnoreCase(title);
    }

    public MedalResponseDTO addmedal(String userEmail, AddMedalDTO addMedalDTO) {
        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        if(!fightRepository.existsById(addMedalDTO.getFight_id())){
            throw new NotFoundException("Fight not found");
        }
        Integer rounds_won = addMedalDTO.getWinner().getRounds_won();
        Integer rounds_lost = addMedalDTO.getWinner().getRounds_lost();
        Integer rounds_played = addMedalDTO.getWinner().getRounds();
        if(rounds_lost == 0){
            Medals medal = Medals.builder()
                    .title("Untouchable Master")
                    .user(user)
                    .build();
            medal = medalsRepository.save(medal);
            return MedalResponseDTO.builder()
                    .id(medal.getId())
                    .title(medal.getTitle())
                    .build();
        }
        if(rounds_won == 0){
            Medals medal = Medals.builder()
                    .title("Unlucky Master")
                    .user(user)
                    .build();
            medal = medalsRepository.save(medal);
            return MedalResponseDTO.builder()
                    .id(medal.getId())
                    .title(medal.getTitle())
                    .build();
        }
        if(rounds_won > rounds_played/2){
            Medals medal = Medals.builder()
                    .title("Master")
                    .user(user)
                    .build();
            medal = medalsRepository.save(medal);
            return MedalResponseDTO.builder()
                    .id(medal.getId())
                    .title(medal.getTitle())
                    .build();
        }
        if(rounds_lost < rounds_played/2){
            Medals medal = Medals.builder()
                    .title("Unlucky")
                    .user(user)
                    .build();
            medal = medalsRepository.save(medal);
            return MedalResponseDTO.builder()
                    .id(medal.getId())
                    .title(medal.getTitle())
                    .build();
        }
        throw new NotFoundException("Medal not found");
    }
}
