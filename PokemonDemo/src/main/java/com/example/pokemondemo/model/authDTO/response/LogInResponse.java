package com.example.pokemondemo.model.authDTO.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogInResponse {
    private Integer id;
    private String email;
    private String username;
    private String token;
}
