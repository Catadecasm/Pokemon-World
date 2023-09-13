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

    @Test
    @Transactional
    public void SaveAndRetrieve_Pokemon() {
        // Crear un usuario
        User user = userRepository.findByEmailIgnoreCase("willy@endava.com").get();

        // Crear un Pokemon DTO
        PokemonDTO pokemonDTO = PokemonDTO.builder()
                .name("my little fire")
                .specie("charmander")
                .build();

        // Guardar el Pokemon en la base de datos
        Pokemon pokemonToSave = Pokemon.builder()
                .name(pokemonDTO.getName())
                .specie(pokemonDTO.getSpecie())
                .user(user)
                .image("dasd")
                .build();
        pokemonToSave = pokemonRepository.save(pokemonToSave);

        // Recuperar el Pokemon de la base de datos
        Pokemon retrievedPokemon = pokemonRepository.findById(pokemonToSave.getId()).orElse(null);

        // Verificar que el Pokemon se ha recuperado correctamente
        assertThat(retrievedPokemon).isNotNull();
        assertThat(retrievedPokemon.getName()).isEqualTo(pokemonDTO.getName());
        assertThat(retrievedPokemon.getSpecie()).isEqualTo(pokemonDTO.getSpecie());
        assertThat(retrievedPokemon.getImage()).isEqualTo("dasd");
        assertThat(retrievedPokemon.getUser()).isEqualTo(user);
    }

    @Test
    public void Save_PokemonWithNullName_ShouldThrowException() {
        // Crear un usuario
        User user = userRepository.findByEmailIgnoreCase("willy@endava.com").get();

        // Crear un Pokemon DTO con nombre nulo
        PokemonDTO pokemonDTO = PokemonDTO.builder()
                .name(null)
                .specie("charmander")
                .build();

        // Intentar guardar un Pokemon con nombre nulo
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
}



