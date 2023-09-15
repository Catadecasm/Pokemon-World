package com.example.pokemondemo.Profiles;

import com.example.pokemondemo.exception.NotFoundException;
import com.example.pokemondemo.model.dto.TrainerPokedexDTO;
import com.example.pokemondemo.service.PokemonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class WatchAllPokemonTest {
    @Autowired
    PokemonService pokemonService;

    @Test
    public void WatchAllExceptions(){
        NotFoundException exception = assertThrows(NotFoundException.class, () -> pokemonService.findAllByFollow("willy@endava.com","notRealuser",0,0));
        assertThat(exception.getMessage()).isEqualTo("The trainer does not exist");

        exception = assertThrows(NotFoundException.class,  () -> pokemonService.findAllByFollow("willy@endava.com","satu",0,0));
        assertThat(exception.getMessage()).isEqualTo("You don't follow this pokémon trainer,\n" +
                " you have to be Professor to watch this information");
}

    @Test
    public void WatchAllShouldReturnThePokemons(){
        TrainerPokedexDTO response = pokemonService.findAllByFollow("willy@endava.com","willy", 2,0);
        assertThat(response).isNotNull();
        assertThat(response.getIndex()).isEqualTo(0);
        assertThat(response.getQuantity()).isEqualTo(2);
        assertThat(response.getResult().size()).isEqualTo(2);
    }

    @Test
    public void WatchAllWithInvalidTrainerShouldThrowNotFoundException() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> pokemonService.findAllByFollow("willy@endava.com", "notRealuser", 0, 0));
        assertThat(exception.getMessage()).isEqualTo("The trainer does not exist");
    }
    @Test
    public void WatchAllWithoutBeingProfessorShouldThrowNotFoundException() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> pokemonService.findAllByFollow("willy@endava.com", "satu", 0, 0));
        assertThat(exception.getMessage()).isEqualTo("You don't follow this pokémon trainer,\n" +
                " you have to be Professor to watch this information");
    }
    @Test
    public void WatchAllWithValidTrainerShouldReturnPokemons() {
        TrainerPokedexDTO response = pokemonService.findAllByFollow("willy@endava.com", "willy", 2, 0);
        assertThat(response).isNotNull();
        assertThat(response.getIndex()).isEqualTo(0);
        assertThat(response.getQuantity()).isEqualTo(2);
        assertThat(response.getResult().size()).isEqualTo(2);
    }
    @Test
    public void WatchAllWithValidTrainerAndNegativeIndexShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> pokemonService.findAllByFollow("willy@endava.com", "willy", 2, -1));
    }
    @Test
    public void WatchAllWithValidTrainerAndLargeQuantityShouldReturnLimitedPokemons() {
        TrainerPokedexDTO response = pokemonService.findAllByFollow("willy@endava.com", "willy", 1000, 0);
        assertThat(response).isNotNull();
        assertThat(response.getIndex()).isEqualTo(0);
        assertThat(response.getQuantity()).isEqualTo(1000);
        assertThat(response.getResult().size()).isLessThanOrEqualTo(1000);
    }
}
