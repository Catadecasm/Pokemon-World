package com.example.pokemondemo;

import com.example.pokemondemo.entity.Pokemon;
import com.example.pokemondemo.entity.User;
import com.example.pokemondemo.model.dto.PokemonDTO;
import com.example.pokemondemo.repository.PokemonRepository;
import com.example.pokemondemo.repository.UserRepository;
import com.example.pokemondemo.service.PokemonService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.pokemondemo.service.pokeapi.PokedexPokemonSpecServiceImplService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    User user;


    @BeforeEach
    public void init() {
        this.pokedexPokemonSpecServiceImpl = new PokedexPokemonSpecServiceImplService();
        Optional<User> userOptional = userRepository.findByEmailIgnoreCase("willy@endava.com");
        this.user = userOptional.orElse(null);
    }

    @Test
    public void shouldSavePokemon() {
        Pokemon defaultPokemon = getDefaultPokemon();

        defaultPokemon = pokemonRepository.save(defaultPokemon);

        Assertions.assertThat(defaultPokemon).isNotNull();
        Assertions.assertThat(defaultPokemon.getId()).isGreaterThan(0);
    }

    //Test to verify that the Pokemon object is correctly stored in the database and can be retrieved correctly
    @Test
    @Transactional
    public void shouldSaveAndReturnPokemon() {;
        Pokemon defaultPokemon = getDefaultPokemon();
        defaultPokemon = pokemonRepository.save(defaultPokemon);

        Pokemon retrievedPokemon = pokemonRepository.findById(defaultPokemon.getId()).orElse(null);

        assertThat(retrievedPokemon).isNotNull();
        assertEquals(retrievedPokemon, defaultPokemon);
    }

    // Test to verify that you cannot save a Pokemon with a null name
    @Test
    public void shouldThrowAnExceptionIfPokemonNameIsNull() {
        Pokemon defaultPokemon = getDefaultPokemon();
        defaultPokemon.setName(null);

        assertThatThrownBy(() -> {
            pokemonRepository.save(defaultPokemon);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    //Test to verify that multiple Pokemon can be saved for the same user
    @Test
    public void shouldSaveMultiplePokemonsForSameUser() {
        Pokemon firstPokemonToSave = getDefaultPokemon();
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


    private Pokemon getDefaultPokemon(){
        return Pokemon.builder()
                .name("charmander")
                .specie("my little fire")
                .image("asd")
                .user(user)
                .build();
    }
}