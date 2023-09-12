package com.example.pokemondemo.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultDTO {
    private Integer rounds;
    private String winner;
}
