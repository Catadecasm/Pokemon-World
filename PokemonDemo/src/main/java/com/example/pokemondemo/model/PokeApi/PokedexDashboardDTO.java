package com.example.pokemondemo.model.PokeApi;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PokedexDashboardDTO {
    private Integer quantity;
    private Integer id;
    private List<SinglePokemonDTO> result;

}
