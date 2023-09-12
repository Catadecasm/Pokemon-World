package com.example.pokemondemo.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FightListDTO {
    private Integer quantity;
    private Integer index;
    private List<FightResultDTO> result;
}