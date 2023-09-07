package com.example.pokemondemo.model.payload.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FightResponseDTO {
    private Integer id;
    private String createAt;
    private String updateAt;
}
