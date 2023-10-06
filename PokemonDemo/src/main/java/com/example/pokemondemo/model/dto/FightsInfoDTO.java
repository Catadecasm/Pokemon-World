package com.example.pokemondemo.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FightsInfoDTO {
    private Integer totalFights;
    private Integer totalWins;
    private Integer totalLosses;
}