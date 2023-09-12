package com.example.pokemondemo.model.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterLeagueDTO {
    private Integer id;
    private String email;
    private String username;
    private LeagueResponseDTO
            league;
}
