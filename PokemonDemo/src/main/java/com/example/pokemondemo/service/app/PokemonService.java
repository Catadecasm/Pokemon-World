package com.example.pokemondemo.service.app;


import java.util.List;

import com.example.pokemondemo.domain.User;
import com.example.pokemondemo.util.*;
import com.example.pokemondemo.domain.Pokemon;
import com.example.pokemondemo.model.DataBase.PokemonDTO;
import com.example.pokemondemo.repository.PokemonRepository;
import com.example.pokemondemo.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final UserRepository userRepository;

    public PokemonService(final PokemonRepository pokemonRepository,
            final UserRepository userRepository) {
        this.pokemonRepository = pokemonRepository;
        this.userRepository = userRepository;
    }

    public List<PokemonDTO> findAll() {
        final List<Pokemon> pokemons = pokemonRepository.findAll(Sort.by("id"));
        return pokemons.stream()
                .map(pokemon -> mapToDTO(pokemon, new PokemonDTO()))
                .toList();
    }

    public PokemonDTO get(final Integer id) {
        return pokemonRepository.findById(id)
                .map(pokemon -> mapToDTO(pokemon, new PokemonDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PokemonDTO pokemonDTO) {
        final Pokemon pokemon = new Pokemon();
        mapToEntity(pokemonDTO, pokemon);
        return pokemonRepository.save(pokemon).getId();
    }

    public void update(final Integer id, final PokemonDTO pokemonDTO) {
        final Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(pokemonDTO, pokemon);
        pokemonRepository.save(pokemon);
    }

    public void delete(final Integer id) {
        pokemonRepository.deleteById(id);
    }

    private PokemonDTO mapToDTO(final Pokemon pokemon, final PokemonDTO pokemonDTO) {
        pokemonDTO.setId(pokemon.getId());
        pokemonDTO.setName(pokemon.getName());
        pokemonDTO.setSpecie(pokemon.getSpecie());
        pokemonDTO.setImage(pokemon.getImage());
        pokemonDTO.setUser(pokemon.getUser() == null ? null : pokemon.getUser().getId());
        return pokemonDTO;
    }

    private Pokemon mapToEntity(final PokemonDTO pokemonDTO, final Pokemon pokemon) {
        pokemon.setName(pokemonDTO.getName());
        pokemon.setSpecie(pokemonDTO.getSpecie());
        pokemon.setImage(pokemonDTO.getImage());
        final User user = pokemonDTO.getUser() == null ? null : userRepository.findById(pokemonDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        pokemon.setUser(user);
        return pokemon;
    }

}
