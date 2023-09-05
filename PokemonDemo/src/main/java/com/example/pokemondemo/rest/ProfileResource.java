package com.example.pokemondemo.rest;

import com.example.pokemondemo.model.profilePayload.request.ActionDTO;
import com.example.pokemondemo.service.app.FollowService;
import com.example.pokemondemo.service.app.PokemonService;
import com.example.pokemondemo.service.security.JWTService;
import com.example.pokemondemo.util.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileResource {

    private final FollowService followService;
    private final PokemonService pokemonService;
    private final JWTService jwtService;

    public ProfileResource(final FollowService followService, PokemonService pokemonService, JWTService jwtService) {
        this.followService = followService;
        this.pokemonService = pokemonService;
        this.jwtService = jwtService;
    }

    // S04 - Follow and unfollow a trainer
    @PostMapping("/{username}/relationship")
    public ResponseEntity<?> followAndUnfollow(@RequestBody ActionDTO msg, HttpServletRequest request, @PathVariable(name = "username") String username) {
        String header = request.getHeader("Authorization");
        String jwt = header.substring(7);
        String userEmail = jwtService.getUserEmail(jwt);
        if (userEmail != null) {
            if (msg.getAction().equals("follow")) {
                try {
                    return ResponseEntity.ok(followService.follow(userEmail, username));
                } catch (NotFoundException e) {
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            } else if (msg.getAction().equals("unfollow")) {
                try {
                    return ResponseEntity.ok(followService.unfollow(userEmail, username));
                } catch (NotFoundException e) {
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            } else {
                return ResponseEntity.badRequest().body("Action not found");
            }
        }
        return ResponseEntity.badRequest().body("User not found");
    }

    // S05 - Get all pokemon of a trainer (with pagination)

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
                return ResponseEntity.ok().body(pokemonService.findAllByFollow(userEmail, username, quantity, offset, null, null));
            } catch (NotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.badRequest().body("User not found");
    }

    // s06 cure pokemons
    @PostMapping("/{username}/cure")
    public ResponseEntity<?> curePokemons(
            @PathVariable(name = "username") String username,
            @RequestParam(name = "id", required = true) Integer Pokemonid,
            @RequestBody ActionDTO msg) {
        if (msg.getAction().equals("cure")) {

        }
        return ResponseEntity.badRequest().body("Bad Action!");
    }

}
