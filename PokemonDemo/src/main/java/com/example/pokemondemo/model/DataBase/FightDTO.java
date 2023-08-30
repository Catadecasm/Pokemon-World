package com.example.pokemondemo.model.DataBase;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FightDTO {

    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer oponentId;

    @NotNull
    private Integer rounds;

    @NotNull
    private LocalDate creation;

    @NotNull
    private LocalDate updatee;

    @NotNull
    @Size(max = 255)
    private String league;

    private List<Integer> userFightUsers;

}
