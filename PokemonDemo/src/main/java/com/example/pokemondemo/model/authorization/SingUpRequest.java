package com.example.pokemondemo.model.authorization;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SingUpRequest {

        @NotNull
        private String email;
        @NotNull
        private String username;
        @NotNull
        private String password;
        @NotNull
        private String role;
}
