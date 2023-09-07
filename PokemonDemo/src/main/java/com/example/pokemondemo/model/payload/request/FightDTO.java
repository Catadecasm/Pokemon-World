package com.example.pokemondemo.model.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

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
