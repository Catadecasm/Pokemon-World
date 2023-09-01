package com.example.pokemondemo.model.PokeApi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SingleEsPokemonDTO {
    private String Name;
    private Integer index;
    private List<String> type;
    private String language;
    private String img_path;
    private String description;
    private Map<String,Integer> stats;
    private List<AbilitiesDTO> abilities;

}
