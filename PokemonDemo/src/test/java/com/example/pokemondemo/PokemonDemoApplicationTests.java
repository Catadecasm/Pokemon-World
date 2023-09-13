package com.example.pokemondemo;

import com.example.pokemondemo.entity.Pokemon;
import com.example.pokemondemo.entity.User;
import com.example.pokemondemo.model.dto.ClassicResponseDTO;
import com.example.pokemondemo.model.dto.PokemonDTO;
import com.example.pokemondemo.model.dto.TrainerPokedexDTO;
import com.example.pokemondemo.model.dto.pokeapi.SingleEsPokemonDTO;
import com.example.pokemondemo.repository.PokemonRepository;
import com.example.pokemondemo.repository.UserRepository;
import com.example.pokemondemo.service.PokemonService;
import com.example.pokemondemo.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.pokemondemo.service.pokeapi.PokedexPokemonSpecServiceImplService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.Arrays;
import java.util.List;

@SpringBootTest
class PokemonDemoApplicationTests {
    @Autowired
    PokemonService pokemonService;
    @Autowired
    PokemonRepository pokemonRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PokedexPokemonSpecServiceImplService pokedexPokemonSpecServiceImpl;

    @Test
    void contextLoads() {


    }

    @Test
    public void Save_Pokemon() {
        this.pokedexPokemonSpecServiceImpl = new PokedexPokemonSpecServiceImplService();
        PokemonDTO pokemonDTO = PokemonDTO.builder()
                .name("my little fire")
                .specie("charmander")
                .build();
        User user = userRepository.findByEmailIgnoreCase("willy@endava.com").get();
        Pokemon pokemonSave = Pokemon.builder()
                .name(pokemonDTO.getName())
                .specie(pokemonDTO.getName())
                .user(user)
                .image("dasd")
                .build();
        pokemonSave = pokemonRepository.save(pokemonSave);
        Assertions.assertThat(pokemonSave).isNotNull();
        Assertions.assertThat(pokemonSave.getId()).isGreaterThan(0);
    }

    //Test to verify that the Pokemon object is correctly stored in the database and can be retrieved correctly
    @Test
    @Transactional
    public void SaveAndRetrieve_Pokemon() {
        User user = userRepository.findByEmailIgnoreCase("willy@endava.com").get();
        PokemonDTO pokemonDTO = PokemonDTO.builder()
                .name("my little fire")
                .specie("charmander")
                .build();
        Pokemon pokemonToSave = Pokemon.builder()
                .name(pokemonDTO.getName())
                .specie(pokemonDTO.getSpecie())
                .user(user)
                .image("dasd")
                .build();
        pokemonToSave = pokemonRepository.save(pokemonToSave);

        Pokemon retrievedPokemon = pokemonRepository.findById(pokemonToSave.getId()).orElse(null);

        assertThat(retrievedPokemon).isNotNull();
        assertThat(retrievedPokemon.getName()).isEqualTo(pokemonDTO.getName());
        assertThat(retrievedPokemon.getSpecie()).isEqualTo(pokemonDTO.getSpecie());
        assertThat(retrievedPokemon.getImage()).isEqualTo("dasd");
        assertThat(retrievedPokemon.getUser()).isEqualTo(user);
    }

    // Test to verify that you cannot save a Pokemon with a null name
    @Test
    public void Save_PokemonWithNullName_ShouldThrowException() {

        User user = userRepository.findByEmailIgnoreCase("willy@endava.com").get();

        PokemonDTO pokemonDTO = PokemonDTO.builder()
                .name(null)
                .specie("charmander")
                .build();

        assertThatThrownBy(() -> {
            Pokemon pokemonToSave = Pokemon.builder()
                    .name(pokemonDTO.getName())
                    .specie(pokemonDTO.getSpecie())
                    .user(user)
                    .image("dasd")
                    .build();
            pokemonRepository.save(pokemonToSave);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    //Test to verify that multiple Pokemon can be saved for the same user
    @Test
    public void Save_MultiplePokemonForUser() {
        User user = userRepository.findByEmailIgnoreCase("willy@endava.com").get();
        PokemonDTO firstPokemonDTO = PokemonDTO.builder()
                .name("my little fire")
                .specie("charmander")
                .build();

        Pokemon firstPokemonToSave = Pokemon.builder()
                .name(firstPokemonDTO.getName())
                .specie(firstPokemonDTO.getSpecie())
                .user(user)
                .image("dasd")
                .build();
        firstPokemonToSave = pokemonRepository.save(firstPokemonToSave);

        PokemonDTO secondPokemonDTO = PokemonDTO.builder()
                .name("Pikachu")
                .specie("Electric")
                .build();

        Pokemon secondPokemonToSave = Pokemon.builder()
                .name(secondPokemonDTO.getName())
                .specie(secondPokemonDTO.getSpecie())
                .user(user)
                .image("pikachu.png")
                .build();
        secondPokemonToSave = pokemonRepository.save(secondPokemonToSave);

        assertThat(firstPokemonToSave).isNotNull();
        assertThat(firstPokemonToSave.getId()).isGreaterThan(0);
        assertThat(secondPokemonToSave).isNotNull();
        assertThat(secondPokemonToSave.getId()).isGreaterThan(0);
    }
}




