package com.example.pokemondemo.model.payload.response;

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
