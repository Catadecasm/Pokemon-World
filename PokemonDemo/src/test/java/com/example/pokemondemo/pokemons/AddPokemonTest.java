package com.example.pokemondemo.pokemons;

import com.example.pokemondemo.model.dto.PokemonRequestDTO;
import com.example.pokemondemo.service.PokemonService;
import com.example.pokemondemo.service.pokeapi.PokedexPokemonSpecServiceImplService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AddPokemonTest {

    @Autowired
    PokemonService pokemonService;
    @Autowired
    PokedexPokemonSpecServiceImplService pokedexPokemonSpecServiceImpl;



}
