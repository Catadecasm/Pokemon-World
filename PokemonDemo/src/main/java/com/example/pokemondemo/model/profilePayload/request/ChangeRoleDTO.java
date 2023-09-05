package com.example.pokemondemo.model.profilePayload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRoleDTO {
    @NotNull
    private String action;
    @NotNull
    private String role;
    @NotNull
    private String username;
}
