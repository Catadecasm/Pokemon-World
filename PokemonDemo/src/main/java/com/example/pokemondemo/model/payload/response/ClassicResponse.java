package com.example.pokemondemo.model.payload.response;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassicResponse {
    private String ResponseCode;
    private String ResponseMessage;
}
