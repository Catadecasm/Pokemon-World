package com.example.pokemondemo.model.dto.pokeapi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class EvolutionChainDTO {
    private List<EvolutionDTO> chain;
    private EvolutionDTO next_evol;

    public EvolutionChainDTO() {
        this.chain = new ArrayList<>();
    }
}
