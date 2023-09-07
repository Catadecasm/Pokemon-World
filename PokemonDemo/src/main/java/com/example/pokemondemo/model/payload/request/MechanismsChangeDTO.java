package com.example.pokemondemo.model.payload.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MechanismsChangeDTO {
    private String name;
    private Integer index;
    private List<String> type;
    private List<String> mechanism;
}
