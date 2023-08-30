package com.example.pokemondemo.service;

import com.example.pokemondemo.domain.League;
import com.example.pokemondemo.domain.Role;
import com.example.pokemondemo.util.*;
import com.example.pokemondemo.domain.User;
import com.example.pokemondemo.model.DataBase.UserDTO;
import com.example.pokemondemo.repository.FightRepository;
import com.example.pokemondemo.repository.LeagueRepository;
import com.example.pokemondemo.repository.RoleRepository;
import com.example.pokemondemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final LeagueRepository leagueRepository;
    private final FightRepository fightRepository;

    public UserService(final UserRepository userRepository, final RoleRepository roleRepository,
            final LeagueRepository leagueRepository, final FightRepository fightRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.leagueRepository = leagueRepository;
        this.fightRepository = fightRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
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
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Integer id) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        fightRepository.findAllByUserFightUsers(user)
                .forEach(fight -> fight.getUserFightUsers().remove(user));
        userRepository.delete(user);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole() == null ? null : user.getRole().getId());
        userDTO.setLeagueid(user.getLeagueid() == null ? null : user.getLeagueid().getId());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        final Role role = userDTO.getRole() == null ? null : roleRepository.findById(userDTO.getRole())
                .orElseThrow(() -> new NotFoundException("role not found"));
        user.setRole(role);
        final League leagueid = userDTO.getLeagueid() == null ? null : leagueRepository.findById(userDTO.getLeagueid())
                .orElseThrow(() -> new NotFoundException("leagueid not found"));
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
