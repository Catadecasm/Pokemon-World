package com.example.pokemondemo.service.app;

import com.example.pokemondemo.domain.*;
import com.example.pokemondemo.model.authDTO.request.LogInRequest;
import com.example.pokemondemo.model.authDTO.request.SingUpRequest;
import com.example.pokemondemo.model.authDTO.response.LogInResponse;
import com.example.pokemondemo.model.authDTO.response.LogOutResponse;
import com.example.pokemondemo.model.authDTO.response.SingUpResponse;
import com.example.pokemondemo.model.payload.request.ChangeRoleDTO;
import com.example.pokemondemo.model.payload.response.ClassicResponse;
import com.example.pokemondemo.service.security.JWTService;
import com.example.pokemondemo.util.*;
import com.example.pokemondemo.model.DataBase.UserDTO;
import com.example.pokemondemo.repository.FightRepository;
import com.example.pokemondemo.repository.LeagueRepository;
import com.example.pokemondemo.repository.UserRepository;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        /*
        User user1 = userRepository.findByEmailIgnoreCase(email).get();
        if(user1.isLogged()){
            throw new NotFoundException("The user is already logged");
        }*/
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
        LogOutResponse logInResponse = null;
        if (this.authenticatedUser == null || !this.authenticatedUser.isLogged()) {
            return logInResponse;
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
                    logInResponse = new LogOutResponse("ok");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    authentication.setAuthenticated(false);
                    this.authenticatedUser.setLogged(false);
                }
            }
        }
        return logInResponse;
    }

    public ClassicResponse changeRole(String emailUser, ChangeRoleDTO changeRoleDTO) {

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
            return ClassicResponse.builder()
                    .ResponseCode("200")
                    .ResponseMessage("Role updated successfully")
                    .build();
        }
        throw new NotFoundException("You don't have not provided adequate credentials to access this resource");
    }

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
        userDTO.setUsername(user.getRealUsername());
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