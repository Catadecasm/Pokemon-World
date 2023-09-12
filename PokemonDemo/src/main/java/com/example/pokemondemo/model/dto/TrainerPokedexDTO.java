package com.example.pokemondemo.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerPokedexDTO {
    @NotNull
    private Integer quantity;
    @NotNull
    private Integer index;
    @NotNull
    private List<PokemonDTO> result;
}