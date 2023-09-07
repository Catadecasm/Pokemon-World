package com.example.pokemondemo.model.payload.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMedalDTO {
    private String league_name;
    private Integer fight_id;
    private String created_at;
    private String updated_at;
    private WinnerDTO winner;

}
