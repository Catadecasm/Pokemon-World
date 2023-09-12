package com.example.pokemondemo.controller.rest;

import com.example.pokemondemo.service.LeagueService;
import com.example.pokemondemo.service.JWTService;
import com.example.pokemondemo.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/leagues", produces = MediaType.APPLICATION_JSON_VALUE)
public class LeagueResource {

    private final LeagueService leagueService;
    private final JWTService jwtService;

    public LeagueResource(final LeagueService leagueService, JWTService jwtService) {
        this.leagueService = leagueService;
        this.jwtService = jwtService;
    }

    @PostMapping("/{id}/register-league")
    public ResponseEntity<?> registerLeague(HttpServletRequest request, @PathVariable final Integer id) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String userEmail = jwtService.getUserEmail(token);
        try{
            return  ResponseEntity.ok(leagueService.registerLeague(userEmail,id));

        }catch (NotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
