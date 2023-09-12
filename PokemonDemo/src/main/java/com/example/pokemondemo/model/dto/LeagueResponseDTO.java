package com.example.pokemondemo.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeagueResponseDTO {
    private Integer id;
    private String name;
}
