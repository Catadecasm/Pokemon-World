package com.example.pokemondemo.pokemons;

import com.example.pokemondemo.exception.NotFoundException;
import com.example.pokemondemo.repository.PokemonRepository;
import com.example.pokemondemo.service.PokemonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DeletePokemonTest {

    @Autowired
    PokemonService pokemonService;
    @Autowired
    PokemonRepository pokemonRepository;

    @Test
    public void DeletePokemonExceptionTest() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> pokemonService.deletePokemon("willy@endava.com", 1, "satu"));
        assertThat(exception.getMessage()).isEqualTo("You can't delete a pokemon to other trainer");

        exception = assertThrows(NotFoundException.class, () -> pokemonService.deletePokemon("willy@endava.com", 14, "willy"));
        assertThat(exception.getMessage()).isEqualTo("The pokemon does not exist, the id does not match with any pokemon");

        exception = assertThrows(NotFoundException.class, () -> pokemonService.deletePokemon("willy@endava.com", 12, "willy"));
        assertThat(exception.getMessage()).isEqualTo("The pokemon does not belong to the trainer");
    }

    @Test
    public void DeletePokemonShouldReturnOk() {
        assertThat(pokemonRepository.findById(55)).isNotEmpty();
        pokemonService.deletePokemon("willy@endava.com",55,"willy");
        assertThat(pokemonRepository.findById(55)).isEmpty();
    }
}
