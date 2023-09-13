package com.example.pokemondemo;

import com.example.pokemondemo.entity.Pokemon;
import com.example.pokemondemo.entity.User;
import com.example.pokemondemo.model.dto.PokemonDTO;
import com.example.pokemondemo.model.dto.pokeapi.SingleEsPokemonDTO;
import com.example.pokemondemo.repository.PokemonRepository;
import com.example.pokemondemo.repository.UserRepository;
import com.example.pokemondemo.service.PokemonService;
import com.example.pokemondemo.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.pokemondemo.service.pokeapi.PokedexPokemonSpecServiceImplService;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void Save_Pokemon(){
        this.pokedexPokemonSpecServiceImpl = new PokedexPokemonSpecServiceImplService();
        PokemonDTO pokemonDTO = PokemonDTO.builder()
                .name("my little fire")
                .specie("charmander")
                .build();
        User user =  userRepository.findByEmailIgnoreCase("willy@endava.com").get();
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


}
