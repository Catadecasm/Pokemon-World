package com.example.pokemondemo.service.pokeapi;

import com.example.pokemondemo.model.dto.pokeapi.SinglePokemonDTO;

public interface PokedexPokemonService {
    public SinglePokemonDTO getPokemon(int id);
}
