package com.example.pokemondemo.model.payload.response;

import com.example.pokemondemo.model.DataBase.PokemonDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerPokedex {
    @NotNull
    private Integer quantity;
    @NotNull
    private Integer index;
    @NotNull
    private List<PokemonDTO> result;
}
