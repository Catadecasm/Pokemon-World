package com.example.pokemondemo.pokeConnection.Intf;

import com.example.pokemondemo.model.PokeApi.SinglePokemonDTO;

public interface PokedexPokemon {
    public SinglePokemonDTO getPokemon(int id);
}
