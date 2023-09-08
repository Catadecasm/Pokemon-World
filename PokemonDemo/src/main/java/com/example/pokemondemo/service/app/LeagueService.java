package com.example.pokemondemo.service.app;


import com.example.pokemondemo.domain.League;
import com.example.pokemondemo.domain.User;
import com.example.pokemondemo.model.payload.response.LeagueResponseDTO;
import com.example.pokemondemo.model.payload.response.RegisterLeagueDTO;
import com.example.pokemondemo.repository.LeagueRepository;
import com.example.pokemondemo.repository.UserRepository;
import com.example.pokemondemo.util.NotFoundException;
import org.springframework.stereotype.Service;


@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final UserRepository userRepository;

    public LeagueService(final LeagueRepository leagueRepository, UserRepository userRepository) {
        this.leagueRepository = leagueRepository;
        this.userRepository = userRepository;
    }


    public RegisterLeagueDTO registerLeague(String userEmail, Integer id) {
        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        if (leagueRepository.findById(id).isEmpty()) {
            throw new NotFoundException("League not found");
        }
        League league = leagueRepository.findById(id).get();
        user.setLeagueid(league);
        userRepository.save(user);
        return RegisterLeagueDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getRealUsername())
                .league(LeagueResponseDTO.builder()
                        .id(user.getLeagueid().getId())
                        .name(user.getLeagueid().getName())
                        .build())
                .build();
    }
}
