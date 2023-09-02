package com.example.pokemondemo.PokeConnection.Intf;

import com.example.pokemondemo.model.PokeApi.EvolutionChainDTO;
import com.example.pokemondemo.model.PokeApi.SingleEsPokemonDTO;
import com.example.pokemondemo.model.PokeApi.SinglePokemonDTO;

public interface PokedexPokemon {
    public SinglePokemonDTO getPokemon(int id);
}
