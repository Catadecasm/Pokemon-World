package com.example.pokemondemo.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FightResultDTO {
    private Integer id;
    private String createAt;
    private String updateAt;
    private String league;
    private ResultDTO result;
    private OpponentDTO opponent;

}
