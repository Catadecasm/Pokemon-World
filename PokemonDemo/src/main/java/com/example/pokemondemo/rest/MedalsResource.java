package com.example.pokemondemo.rest;

import com.example.pokemondemo.model.payload.request.AddMedalDTO;
import com.example.pokemondemo.service.app.MedalsService;
import com.example.pokemondemo.service.security.JWTService;
import com.example.pokemondemo.util.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/medalss", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedalsResource {

    private final MedalsService medalsService;
    private final JWTService jwtService;

    public MedalsResource(final MedalsService medalsService, JWTService jwtService) {
        this.medalsService = medalsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/add-medals")
    public ResponseEntity<?> addmedal(HttpServletRequest request, AddMedalDTO addMedalDTO){
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String userEmail = jwtService.getUserEmail(token);
        try{
            return  ResponseEntity.ok(medalsService.addmedal(userEmail, addMedalDTO));
        }catch (NotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
