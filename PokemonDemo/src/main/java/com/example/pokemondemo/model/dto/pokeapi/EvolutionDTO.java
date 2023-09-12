package com.example.pokemondemo.model.dto.pokeapi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EvolutionDTO {
    private String name;
    private String detailed_url;
    public EvolutionDTO() {
        this.name = "";
        this.detailed_url = "";
    }
}
