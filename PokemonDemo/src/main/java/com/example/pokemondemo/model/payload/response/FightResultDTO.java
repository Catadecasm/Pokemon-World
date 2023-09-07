package com.example.pokemondemo.model.payload.response;

import com.example.pokemondemo.model.payload.request.OpponentDTO;
import com.example.pokemondemo.model.payload.request.WinnerDTO;
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
