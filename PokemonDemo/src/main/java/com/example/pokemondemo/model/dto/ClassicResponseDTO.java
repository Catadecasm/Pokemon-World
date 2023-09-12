package com.example.pokemondemo.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassicResponseDTO {
    private String ResponseCode;
    @JsonProperty("ResponseMessage")
    private String ResponseMessage;
}