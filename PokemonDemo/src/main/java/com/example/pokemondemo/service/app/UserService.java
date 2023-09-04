package com.example.pokemondemo.service.app;

import com.example.pokemondemo.domain.*;
import com.example.pokemondemo.model.authDTO.request.LogInRequest;
import com.example.pokemondemo.model.authDTO.request.SingUpRequest;
import com.example.pokemondemo.model.authDTO.response.LogInResponse;
import com.example.pokemondemo.model.authDTO.response.LogOutResponse;
import com.example.pokemondemo.model.authDTO.response.SingUpResponse;
import com.example.pokemondemo.service.security.JWTService;
import com.example.pokemondemo.util.*;
import com.example.pokemondemo.model.DataBase.UserDTO;
import com.example.pokemondemo.repository.FightRepository;
import com.example.pokemondemo.repository.LeagueRepository;
import com.example.pokemondemo.repository.UserRepository;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final LeagueRepository leagueRepository;
    private final FightRepository fightRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(final UserRepository userRepository, final LeagueRepository leagueRepository, final FightRepository fightRepository, PasswordEncoder passwordEncoder, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.leagueRepository = leagueRepository;
        this.fightRepository = fightRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public SingUpResponse signUpUser(SingUpRequest signUpRequest) {
        Role role = DefineRole(signUpRequest.getRole());

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(role)
                .leagueid(leagueRepository.findByNameIgnoreCase("Bronze"))
                .userMedalss(new HashSet<Medals>())
                .userPokemons(new HashSet<Pokemon>())
                .userFightFights(new HashSet<Fight>())
                .followerFollows(new HashSet<Follow>())
                .followedFollows(new HashSet<Follow>())
                .build();
        Integer id = userRepository.save(user).getId();
        return SingUpResponse.builder()
                .username(user.getRealUsername())
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .id(id)
                .build();
    }

    private Role DefineRole(String role) {
        if (role == null) {
            return Role.TRAINER;
        }
        role = role.toUpperCase();
        switch (role) {
            case "ADMIN":
                return Role.ADMIN;
            case "TRAINER":
                return Role.TRAINER;
            case "DOCTOR":
                return Role.DOCTOR;
            default:
                return Role.TRAINER;
        }
    }

    public LogInResponse logInUser(LogInRequest logInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken
                        (logInRequest.getEmail()
                        , logInRequest.getPassword()));
        User user = userRepository.findByEmailIgnoreCase(logInRequest.getEmail())
                .orElseThrow((NotFoundException::new));
        var token = jwtService.generateToken(user);
        return LogInResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .token(token)
                .build();
    }
/*
    public LogOutResponse logOutUser(LogInRequest singUpRequest) {

    }*/

    // CRUD operations
    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream().map(user -> mapToDTO(user, new UserDTO())).toList();
    }

    public UserDTO get(final Integer id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Integer id, final UserDTO userDTO) {
        final User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Integer id) {
        final User user = userRepository.findById(id).orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        fightRepository.findAllByUserFightUsers(user).forEach(fight -> fight.getUserFightUsers().remove(user));
        userRepository.delete(user);

    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole() == null ? null : String.valueOf(user.getRole()));
        userDTO.setLeagueid(user.getLeagueid() == null ? null : user.getLeagueid().getId());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        final Role role = userDTO.getRole() == null ? null : DefineRole(userDTO.getRole());
        user.setRole(role);
        final League leagueid = userDTO.getLeagueid() == null ? null : leagueRepository.findById(userDTO.getLeagueid()).orElseThrow(() -> new NotFoundException("leagueid not found"));
        user.setLeagueid(leagueid);
        return user;
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public boolean usernameExists(final String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }



}
