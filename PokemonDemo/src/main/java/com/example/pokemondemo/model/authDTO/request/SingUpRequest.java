package com.example.pokemondemo.model.authDTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingUpRequest {

        private String email;
        private String username;
        private String password;
        private String role;
}
