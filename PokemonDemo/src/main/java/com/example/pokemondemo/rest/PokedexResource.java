package com.example.pokemondemo.rest;

import com.example.pokemondemo.pokeConnection.Impl.EvolutionChainService;
import com.example.pokemondemo.pokeConnection.Impl.PokedexPokemonService;
import com.example.pokemondemo.pokeConnection.Impl.PokedexPokemonSpecService;
import com.example.pokemondemo.model.PokeApi.EvolutionChainDTO;
import com.example.pokemondemo.model.PokeApi.PokedexDashboardDTO;
import com.example.pokemondemo.model.PokeApi.SingleEsPokemonDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pokedex", produces = MediaType.APPLICATION_JSON_VALUE)
public class PokedexResource {

    private final PokedexPokemonService pokedexpokemonService;
    private final PokedexPokemonSpecService pokedexPokemonSpecService;
    private final EvolutionChainService evolutionChainService;

    public PokedexResource(PokedexPokemonService pokeApi, EvolutionChainService evolutionChainService, PokedexPokemonSpecService pokedexPokemonSpecService) {
        this.pokedexpokemonService = pokeApi;
        this.evolutionChainService = evolutionChainService;
        this.pokedexPokemonSpecService = pokedexPokemonSpecService;
    }

    // Rest endpoint for getting a list of pokemon with a given quantity and offset
    @GetMapping("/pokemon")
    public PokedexDashboardDTO getPokemonList(
            @RequestParam(name = "quantity", required = true) int quantity,
            @RequestParam(name = "offset", required = true) int offset
    ) {
        return pokedexpokemonService.getPokedexDashboard(offset,quantity);
    }

    // Rest endpoint for getting specs a pokemon by name
    @GetMapping("/{language}/pokemon/{name}")
    public SingleEsPokemonDTO getPokemonSpecs(@PathVariable String name, @PathVariable String language) {
        try{
            int id = Integer.parseInt(name);
            return pokedexPokemonSpecService.getEsPokemon(id, language);
        } catch (NumberFormatException e) {
            return pokedexPokemonSpecService.getEsPokemon(name, language);
        }
    }

    // Rest endpoint for getting a pokemon evolution chain by name
    @GetMapping("/{language}/pokemon/evolution-chain/{name}")
    public EvolutionChainDTO getEvolutionChain(@PathVariable String name, @PathVariable String language) {
        try{
            int id = Integer.parseInt(name);
            return evolutionChainService.getEvolutionChain(id, language);
        } catch (NumberFormatException e) {
        return evolutionChainService.getEvolutionChain(name, language);
        }
    }
}
