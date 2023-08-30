package com.example.pokemondemo.PokeConnection;

import com.example.pokemondemo.model.DataBase.PokemonDTO;
import com.example.pokemondemo.model.PokeApi.SingleEsPokemonDTO;
import com.example.pokemondemo.model.PokeApi.SinglePokemonDTO;

public interface Connection {
    public SinglePokemonDTO getPokemon(int id);
    public SinglePokemonDTO getPokemon(String name);
    public SingleEsPokemonDTO getEsPokemon(int id, String language);
    public SingleEsPokemonDTO getEsPokemon(String name, String language);
}
