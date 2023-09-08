package com.example.pokemondemo.rest;

import com.example.pokemondemo.model.DataBase.PokemonDTO;
import com.example.pokemondemo.model.payload.request.MechanismsChangeDTO;
import com.example.pokemondemo.service.app.PokemonService;
import com.example.pokemondemo.service.security.JWTService;
import com.example.pokemondemo.util.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping(value = "/api/pokemons", produces = MediaType.APPLICATION_JSON_VALUE)
public class PokemonResource {

    private final PokemonService pokemonService;
    private final JWTService jwtService;

    public PokemonResource(final PokemonService pokemonService, JWTService jwtService) {
        this.pokemonService = pokemonService;
        this.jwtService = jwtService;
    }

    @GetMapping("/{username}/AllPokemons")
    public ResponseEntity<?> getFollows(HttpServletRequest request,
                                        @PathVariable(name = "username") String username,
                                        @RequestParam(name = "offset", required = true) int offset,
                                        @RequestParam(name = "quantity", required = true) int quantity) {
        String header = request.getHeader("Authorization");
        String jwt = header.substring(7);
        String userEmail = jwtService.getUserEmail(jwt);
        if (userEmail != null) {
            try {
                return ResponseEntity.ok().body(pokemonService.findAllByFollow(userEmail, username, quantity, offset));
            } catch (NotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.badRequest().body("User not found");
    }

    @PostMapping("/pokemon-trainer/{username}/add-pokemon")
    public ResponseEntity<?> addNewPokemon(HttpServletRequest request, @RequestBody PokemonDTO pokemonDTO, @PathVariable String username) {
        String header = request.getHeader("Authorization");
        String jwt = header.substring(7);
        String userEmail = jwtService.getUserEmail(jwt);
        if (!isPokemonDTOValid(pokemonDTO)) {
            return ResponseEntity.badRequest().body("The json recived is not valid");
        }
        try {
            return ResponseEntity.ok(pokemonService.addNewPokemon(userEmail, pokemonDTO, username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/pokemon/{username}/update-pokemon")
    public ResponseEntity<?> updatePokemon(HttpServletRequest request, @RequestBody PokemonDTO pokemonDTO, @PathVariable String username) {
        String header = request.getHeader("Authorization");
        String jwt = header.substring(7);
        String userEmail = jwtService.getUserEmail(jwt);
        if (!isPokemonDTOValid(pokemonDTO)) {
            return ResponseEntity.badRequest().body("The json recived is not valid");
        }
        try {
            return ResponseEntity.ok(pokemonService.updatePokemon(userEmail, pokemonDTO, username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/pokemon/{username}/delete-pokemon/{pokemonId}")
    public ResponseEntity<?> deletePokemon(HttpServletRequest request, @PathVariable Integer pokemonId, @PathVariable String username) {
        String header = request.getHeader("Authorization");
        String jwt = header.substring(7);
        String userEmail = jwtService.getUserEmail(jwt);
        try {
            return ResponseEntity.ok(pokemonService.deletePokemon(userEmail, pokemonId, username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @PutMapping("/pokemon/update-mecahisms")
    public ResponseEntity<?> updateMecha(HttpServletRequest request, @RequestBody MechanismsChangeDTO mechanismsChangeDTO) {
        String header = request.getHeader("Authorization");
        String jwt = header.substring(7);
        String userEmail = jwtService.getUserEmail(jwt);

        try {
            return ResponseEntity.ok(pokemonService.updateMecha(userEmail, mechanismsChangeDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public static boolean isPokemonDTOValid(PokemonDTO pokemonDTO) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        // Realiza la validación del objeto PokemonDTO
        Set<ConstraintViolation<PokemonDTO>> violations = validator.validate(pokemonDTO);

        // Si no hay violaciones, el PokemonDTO está bien armado
        return violations.isEmpty();
    }

}
