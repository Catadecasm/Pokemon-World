package com.example.pokemondemo.service.pokeapi;

import com.example.pokemondemo.model.dto.pokeapi.SingleEsPokemonDTO;

public interface PokedexPokemonSpecsService {
    public SingleEsPokemonDTO getEsPokemon(int id, String language);
    public SingleEsPokemonDTO getEsPokemon(String name, String language);
}
