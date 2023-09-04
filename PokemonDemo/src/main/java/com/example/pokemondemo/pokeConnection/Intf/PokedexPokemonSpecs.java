package com.example.pokemondemo.pokeConnection.Intf;

import com.example.pokemondemo.model.PokeApi.SingleEsPokemonDTO;

public interface PokedexPokemonSpecs {
    public SingleEsPokemonDTO getEsPokemon(int id, String language);
    public SingleEsPokemonDTO getEsPokemon(String name, String language);
}
