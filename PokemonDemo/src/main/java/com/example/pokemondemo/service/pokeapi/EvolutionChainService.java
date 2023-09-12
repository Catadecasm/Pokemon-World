package com.example.pokemondemo.service.pokeapi;

import com.example.pokemondemo.model.dto.pokeapi.EvolutionChainDTO;

public interface EvolutionChainService {
    public EvolutionChainDTO getEvolutionChain(int id, String language);
    public EvolutionChainDTO getEvolutionChain(String name, String language);
}
