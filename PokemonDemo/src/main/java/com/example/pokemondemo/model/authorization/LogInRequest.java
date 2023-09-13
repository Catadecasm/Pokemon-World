package com.example.pokemondemo.model.authorization;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogInRequest {
        private String email;
        private String password;
}
