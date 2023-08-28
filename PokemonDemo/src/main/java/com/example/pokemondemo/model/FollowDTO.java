package com.example.pokemondemo.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FollowDTO {

    private Integer id;

    @NotNull
    private Integer followed;

    @NotNull
    private Integer follower;

}
