package com.example.pokemondemo.pokemons;

import com.example.pokemondemo.entity.Pokemon;
import com.example.pokemondemo.exception.NotFoundException;
import com.example.pokemondemo.model.dto.ClassicResponseDTO;
import com.example.pokemondemo.model.dto.PokemonDTO;
import com.example.pokemondemo.repository.PokemonRepository;
import com.example.pokemondemo.service.PokemonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UpdatePokemonTest {

    @Autowired
    PokemonService pokemonService;

    @Autowired
    PokemonRepository pokemonRepository;

    private static PokemonDTO pokemonDTO;

    @BeforeAll
    public static void setUp() {
        pokemonDTO = PokemonDTO.builder()
                .name("New bulbasaur")
                .id(1)
                .specie("bulbasaur")
                .type(new ArrayList<String>())
                .build();
    }

    @Test
    public void updatePokemonExceptionTest() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> pokemonService.updatePokemon("willy@endava.com", pokemonDTO, "satu"));
        assertThat(exception.getMessage()).isEqualTo("You can't update a pokemon to other trainer");

        pokemonDTO.setId(14);
        exception = assertThrows(NotFoundException.class, () -> pokemonService.updatePokemon("willy@endava.com", pokemonDTO, "willy"));
        assertThat(exception.getMessage()).isEqualTo("The pokemon does not exist, the id does not match with any pokemon");

        pokemonDTO.setId(12);
        exception = assertThrows(NotFoundException.class, () -> pokemonService.updatePokemon("willy@endava.com", pokemonDTO, "willy"));
        assertThat(exception.getMessage()).isEqualTo("The pokemon does not belong to the trainer");

    }

    @Test
    public void updateShouldReturnOk() {
        pokemonDTO.setId(1);
        ClassicResponseDTO response = pokemonService.updatePokemon("willy@endava.com", pokemonDTO, "willy");
        assertThat(response.getResponseCode()).isEqualTo("OK");
        assertThat(response.getResponseMessage()).isEqualTo("The pokemon " + pokemonDTO.getName() + " updated to " + "willy");
        Pokemon pokemon = pokemonRepository.findById(pokemonDTO.getId()).get();
        assertThat(pokemon.getName()).isEqualTo(pokemonDTO.getName());
        // Rollback
        pokemonDTO.setName("My bulbasaur");
        pokemonService.updatePokemon("willy@endava.com", pokemonDTO, "willy");
    }
    @Test
    public void updatePokemonShouldUpdateTypeTest() {
        pokemonDTO.setId(1);
        pokemonDTO.getType().add("Grass");
        ClassicResponseDTO response = pokemonService.updatePokemon("willy@endava.com", pokemonDTO, "willy");
        assertThat(response.getResponseCode()).isEqualTo("OK");
        assertThat(response.getResponseMessage()).isEqualTo("The pokemon " + pokemonDTO.getName() + " updated to willy");
        pokemonService.updatePokemon("willy@endava.com", pokemonDTO, "willy");
    }

}
