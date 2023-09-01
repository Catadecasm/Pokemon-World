package com.example.pokemondemo.model.PokeApi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Setter
public class EvolutionChainDTO {
    private List<EvolutionDTO> chain;
    private EvolutionDTO next_evol;
}
