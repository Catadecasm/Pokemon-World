package com.example.pokemondemo.model.DataBase;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class PokemonDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String specie;

    @NotNull
    private List<String> type;

}
