package com.example.pokemondemo.service.app;


import java.util.List;

import com.example.pokemondemo.domain.*;
import com.example.pokemondemo.model.DataBase.PokemonDTO;
import com.example.pokemondemo.model.profilePayload.response.TrainerPokedex;
import com.example.pokemondemo.repository.FollowRepository;
import com.example.pokemondemo.util.*;
import com.example.pokemondemo.repository.PokemonRepository;
import com.example.pokemondemo.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public PokemonService(final PokemonRepository pokemonRepository,
                          final UserRepository userRepository, FollowRepository followRepository) {
        this.pokemonRepository = pokemonRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    public TrainerPokedex findAllByFollow(String userEmail, String FollowedUser, Integer quantity, Integer offset
            , String filter, String SortBy) {

        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        User followed = userRepository.findByUsernameIgnoreCase(FollowedUser);

        if (followed == null) {
            throw new NotFoundException("The trainer does not exist");
        }
        if (!user.getRole().equals(Role.DOCTOR)) {
            Follow follow = followRepository.findFollowByFollowedAndFollower(followed, user);
            if (follow == null) {
                throw new NotFoundException("You don't follow this pokémon trainer.");
            }
        }
        return findAllByUser(FollowedUser, quantity, offset);

    }


    public TrainerPokedex findAllByUser(String userEmail, Integer quantity, Integer offset) {
        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        Pageable pageable = PageRequest.of(offset, quantity, Sort.by("id"));
        List<Pokemon> pokemons = pokemonRepository.findAllByUser(user, pageable);
        return TrainerPokedex.builder()
                .quantity(quantity)
                .index(offset)
                .result(pokemons.stream().map(pokemon -> MapToDTO(pokemon, new PokemonDTO())).toList())
                .build();
    }

    private PokemonDTO MapToDTO(Pokemon pokemon, PokemonDTO pokemonDTO) {
        pokemonDTO.setId(pokemon.getId());
        pokemonDTO.setName(pokemon.getName());
        pokemonDTO.setSpecie(pokemon.getSpecie());
        pokemonDTO.setType(pokemon.getPokemonidTypes().stream().map(Type::getName).toList());
        return pokemonDTO;
    }

}
