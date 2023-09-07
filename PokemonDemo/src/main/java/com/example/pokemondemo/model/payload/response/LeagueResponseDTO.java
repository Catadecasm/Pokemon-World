package com.example.pokemondemo.model.payload.response;

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
