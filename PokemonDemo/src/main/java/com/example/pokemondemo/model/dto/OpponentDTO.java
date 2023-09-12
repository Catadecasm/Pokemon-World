package com.example.pokemondemo.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpponentDTO {
    private Integer id;
    private String email;
    private String username;
    private Integer winned_rounds;
}
