package com.example.pokemondemo.model.payload.response;


import lombok.*;

import java.util.List;

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
