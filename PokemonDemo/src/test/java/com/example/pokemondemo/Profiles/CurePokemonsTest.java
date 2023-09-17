package com.example.pokemondemo.Profiles;


import com.example.pokemondemo.exception.NotFoundException;
import com.example.pokemondemo.model.dto.ClassicResponseDTO;
import com.example.pokemondemo.service.PokemonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CurePokemonsTest {

    @Autowired
    PokemonService pokemonService;

    @Test
    public void CureExceptionTest() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> pokemonService.curePokemon("willy@endava.com", 1, "willy"));
        assertThat(exception.getMessage()).isEqualTo("You don't have not provided adequate credentials to access this resource");

        exception = assertThrows(NotFoundException.class, () -> pokemonService.curePokemon("drew@endava.com", 1, "NotRealUser"));
        assertThat(exception.getMessage()).isEqualTo("The trainer does not exist");

        exception = assertThrows(NotFoundException.class, () -> pokemonService.curePokemon("drew@endava.com", 8, "willy"));
        assertThat(exception.getMessage()).isEqualTo("The pokemon does not belong to the trainer");

    }

    @Test
    public void CureShouldReturnOk() {
        ClassicResponseDTO response = pokemonService.curePokemon("drew@endava.com", 1, "willy");
        assertThat(response.getResponseCode()).isEqualTo("200");
        assertThat(response.getResponseMessage()).isEqualTo("You have cured the " + "My bulbassaur" + " of " + "willy");
    }

}
