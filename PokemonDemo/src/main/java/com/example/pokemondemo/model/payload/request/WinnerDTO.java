package com.example.pokemondemo.model.payload.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WinnerDTO {
    private Integer id;
    private Integer rounds;
    private Integer rounds_won;
    private Integer rounds_lost;
}
