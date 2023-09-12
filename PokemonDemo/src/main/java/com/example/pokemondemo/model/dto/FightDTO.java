package com.example.pokemondemo.model.dto;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FightDTO {
    private String league;
    private OpponentDTO opponent_1;
    private OpponentDTO opponent_2;
    private Integer rounds;
    private String createAt;
    private String updateAt;
}
