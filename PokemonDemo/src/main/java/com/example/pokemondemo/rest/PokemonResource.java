package com.example.pokemondemo.rest;

import com.example.pokemondemo.model.DataBase.PokemonDTO;
import com.example.pokemondemo.service.app.PokemonService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/pokemons", produces = MediaType.APPLICATION_JSON_VALUE)
public class PokemonResource {

    private final PokemonService pokemonService;

    public PokemonResource(final PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    public ResponseEntity<List<PokemonDTO>> getAllPokemons() {
        return ResponseEntity.ok(pokemonService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokemonDTO> getPokemon(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(pokemonService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createPokemon(@RequestBody @Valid final PokemonDTO pokemonDTO) {
        final Integer createdId = pokemonService.create(pokemonDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updatePokemon(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final PokemonDTO pokemonDTO) {
        pokemonService.update(id, pokemonDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePokemon(@PathVariable(name = "id") final Integer id) {
        pokemonService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
