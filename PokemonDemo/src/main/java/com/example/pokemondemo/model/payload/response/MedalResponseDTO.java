package com.example.pokemondemo.model.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedalResponseDTO {
    private Integer id;
    private String title;
}
