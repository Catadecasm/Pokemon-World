package com.example.pokemondemo.controller.rest;

import com.example.pokemondemo.service.pokeapi.EvolutionChainServiceServiceImpl;
import com.example.pokemondemo.service.pokeapi.PokedexPokemonServiceServiceImpl;
import com.example.pokemondemo.service.pokeapi.PokedexPokemonSpecServiceImplService;
import com.example.pokemondemo.model.dto.pokeapi.EvolutionChainDTO;
import com.example.pokemondemo.model.dto.pokeapi.PokedexDashboardDTO;
import com.example.pokemondemo.model.dto.pokeapi.SingleEsPokemonDTO;
import com.example.pokemondemo.service.pokeapi.EvolutionChainService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pokedex", produces = MediaType.APPLICATION_JSON_VALUE)
public class PokedexResource {

    private final PokedexPokemonServiceServiceImpl pokedexpokemonServiceImpl;
    private final PokedexPokemonSpecServiceImplService pokedexPokemonSpecServiceImpl;
    private final EvolutionChainServiceServiceImpl evolutionChainServiceImpl;
    private final EvolutionChainService evolutionChainService;

    public PokedexResource(PokedexPokemonServiceServiceImpl pokeApi, EvolutionChainServiceServiceImpl evolutionChainServiceImpl, PokedexPokemonSpecServiceImplService pokedexPokemonSpecServiceImpl, EvolutionChainService evolutionChainService) {
        this.pokedexpokemonServiceImpl = pokeApi;
        this.evolutionChainServiceImpl = evolutionChainServiceImpl;
        this.pokedexPokemonSpecServiceImpl = pokedexPokemonSpecServiceImpl;
        this.evolutionChainService = evolutionChainService;
    }

    // Rest endpoint for getting a list of pokemon with a given quantity and offset
    @GetMapping("/pokemon")
    public PokedexDashboardDTO getPokemonList(
            @RequestParam(name = "quantity", required = true) int quantity,
            @RequestParam(name = "offset", required = true) int offset
    ) {
        return pokedexpokemonServiceImpl.getPokedexDashboard(offset,quantity);
    }

    // Rest endpoint for getting specs a pokemon by name
    @GetMapping("/{language}/pokemon/{name}")
    public SingleEsPokemonDTO getPokemonSpecs(@PathVariable String name, @PathVariable String language) {
        try{
            int id = Integer.parseInt(name);
            return pokedexPokemonSpecServiceImpl.getEsPokemon(id, language);
        } catch (NumberFormatException e) {
            return pokedexPokemonSpecServiceImpl.getEsPokemon(name, language);
        }
    }

    // Rest endpoint for getting a pokemon evolution chain by name
    @GetMapping("/{language}/pokemon/evolution-chain/{name}")
    public EvolutionChainDTO getEvolutionChain(@PathVariable String name, @PathVariable String language) {
        try{
            int id = Integer.parseInt(name);
            return evolutionChainService.getEvolutionChain(id, language);
            //return evolutionChainService.getEvolutionChain(id, language);
        } catch (NumberFormatException e) {
        return evolutionChainServiceImpl.getEvolutionChain(name, language);
        }
    }
}
