package com.example.pokemondemo.model.authorization;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
