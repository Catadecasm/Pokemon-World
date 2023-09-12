package com.example.pokemondemo.model.dto.pokeapi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Array;
import java.util.List;

@Getter
@Setter
@ToString
public class SinglePokemonDTO {
    private String Name;
    private Integer Id;
    private List<String> types;
    private String img_path;
}
