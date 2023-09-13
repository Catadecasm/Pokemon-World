package com.example.pokemondemo.service;

import com.example.pokemondemo.entity.*;
import com.example.pokemondemo.model.authorization.LogInRequest;
import com.example.pokemondemo.model.authorization.SingUpRequest;
import com.example.pokemondemo.model.authorization.LogInResponse;
import com.example.pokemondemo.model.authorization.LogOutResponse;
import com.example.pokemondemo.model.authorization.SingUpResponse;
import com.example.pokemondemo.model.dto.ChangeRoleDTO;
import com.example.pokemondemo.model.dto.ClassicResponseDTO;
import com.example.pokemondemo.exception.*;
import com.example.pokemondemo.model.dto.UserDTO;
import com.example.pokemondemo.repository.FightRepository;
import com.example.pokemondemo.repository.LeagueRepository;
import com.example.pokemondemo.repository.UserRepository;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private User authenticatedUser;
    private final UserDetailsService userDetailsService;


    public UserService(final UserRepository userRepository, final LeagueRepository leagueRepository, final FightRepository fightRepository, PasswordEncoder passwordEncoder, JWTService jwtService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.leagueRepository = leagueRepository;
        this.fightRepository = fightRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public SingUpResponse signUpUser(SingUpRequest signUpRequest) {
        Role role = null;
        try {
            role = DefineRole(signUpRequest.getRole());

        } catch (NotFoundException e) {
            throw new NotFoundException("The role does not exist");
        }
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(signUpRequest.getEmail());
        if (!matcher.matches()) {
            throw new NotFoundException("The email is not valid");
        }
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
        Integer id = null;
        try {
            id = userRepository.save(user).getId();
        } catch (Exception e) {
            throw new NotFoundException("The username or email already exists");
        }
        return SingUpResponse.builder()
                .username(user.getRealUsername())
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .id(id)
                .build();
    }

    private Role DefineRole(String role) {
        if (role == null) {
            throw new NotFoundException("The role does not exist");
        }
        role = role.toUpperCase();
        switch (role) {
            case "ADMIN":
                return Role.ADMIN;
            case "DOCTOR":
                return Role.DOCTOR;
            case "PROFESSOR":
                return Role.PROFESSOR;
            case "TRAINER":
                return Role.TRAINER;
            default:
                throw new NotFoundException("The role does not exist");
        }
    }

    public LogInResponse logInUser(LogInRequest logInRequest) {
        String email = logInRequest.getEmail();
        User user1 = userRepository.findByEmailIgnoreCase(email).get();
        if(user1.isLogged()){
            throw new NotFoundException("The user is already logged");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken
                        (logInRequest.getEmail()
                                , logInRequest.getPassword()));

        User user = userRepository.findByEmailIgnoreCase(logInRequest.getEmail())
                .orElseThrow((NotFoundException::new));

        User authenticatedUserObject = (User) authentication.getPrincipal();
        authenticatedUserObject.setLogged(true);
        this.authenticatedUser = authenticatedUserObject;

        var token = jwtService.generateToken(user);
        return LogInResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getRealUsername())
                .token(token)
                .build();
    }

    public LogOutResponse logOutUser(String token) {
        LogOutResponse logOutResponse = null;

        // Verificar si el usuario está autenticado
        if (this.authenticatedUser == null || !this.authenticatedUser.isLogged()) {
            throw new NotFoundException("User not found");
        }

        if (token != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            String userEmail = jwtService.getUserEmail(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if (userDetails != null) {
                boolean isLogoutAdmitted = userEmail.equalsIgnoreCase(
                        String.valueOf(this.authenticatedUser.getEmail()));
                if (isLogoutAdmitted) {
                    User user = userRepository.findByEmailIgnoreCase(userEmail)
                            .orElseThrow((NotFoundException::new));
                    user.setLogged(false);
                    userRepository.save(user);
                    logOutResponse = new LogOutResponse("ok");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    authentication.setAuthenticated(false);
                    this.authenticatedUser.setLogged(false);
                }
            }
        }
        return logOutResponse;
    }

    public ClassicResponseDTO changeRole(String emailUser, ChangeRoleDTO changeRoleDTO) {

        User user = userRepository.findByEmailIgnoreCase(emailUser).get();
        if (user.getRole().equals(Role.ADMIN)) {

            User userToChange = userRepository.findByUsernameIgnoreCase(changeRoleDTO.getUsername());
            if (userToChange == null) {
                throw new NotFoundException("The trainer does not exist");
            }
            if (userToChange.getRole().name().equals(changeRoleDTO.getRole())) {
                throw new NotFoundException("The trainer already has this role");
            }
            try {
                Role role = Role.valueOf(changeRoleDTO.getRole());
            } catch (IllegalArgumentException e) {
                throw new NotFoundException("The role does not exist");
            }
            userToChange.setRole(DefineRole(changeRoleDTO.getRole()));
            userRepository.save(userToChange);
            return ClassicResponseDTO.builder()
                    .ResponseCode("200")
                    .ResponseMessage("Role updated successfully")
                    .build();
        }
        throw new NotFoundException("You don't have not provided adequate credentials to access this resource");
    }

}