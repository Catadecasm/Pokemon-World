package com.example.pokemondemo.model.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CombatHistoryDTO {
    private String trainerId;
    private String leagueId;
}
