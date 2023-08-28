package com.example.pokemondemo.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LeagueDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String name;

}
