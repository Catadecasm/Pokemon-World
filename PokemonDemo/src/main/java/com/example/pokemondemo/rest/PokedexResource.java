package com.example.pokemondemo.rest;

import com.example.pokemondemo.PokeConnection.PokeApi;
import com.example.pokemondemo.model.PokeApi.SingleEsPokemonDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/pokedex",produces = MediaType.APPLICATION_JSON_VALUE)
public class PokedexResource {
    private final PokeApi pokeApi;

    public PokedexResource(PokeApi pokeApi) {
        this.pokeApi = pokeApi;
    }

    @GetMapping("/{id}")
    public SingleEsPokemonDTO getPokedex(@PathVariable(name = "id") final Integer id){
        return pokeApi.getEsPokemon(id,"fr");
    }
}
