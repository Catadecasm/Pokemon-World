package com.example.pokemondemo.PokeConnection.Intf;

import com.example.pokemondemo.model.PokeApi.EvolutionChainDTO;

public interface EvolutionChain {
    public EvolutionChainDTO getEvolutionChain(int id, String language);
    public EvolutionChainDTO getEvolutionChain(String name, String language);
}
