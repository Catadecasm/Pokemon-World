package com.example.pokemondemo.controller.rest;

import com.example.pokemondemo.model.dto.FightDTO;
import com.example.pokemondemo.service.FightService;
import com.example.pokemondemo.service.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/fights", produces = MediaType.APPLICATION_JSON_VALUE)
public class FightResource {

    private final FightService fightService;
    private final JWTService jwtService;

    public FightResource(final FightService fightService, JWTService jwtService) {
        this.fightService = fightService;
        this.jwtService = jwtService;
    }

    @PostMapping("/create-fight")
    public ResponseEntity<?> createFight(HttpServletRequest request, @RequestBody FightDTO fightDTO) {

        String header = request.getHeader("Authorization");
        String jwt = header.substring(7);
        String userEmail = jwtService.getUserEmail(jwt);

        try {
            return ResponseEntity.ok(fightService.createFight(userEmail,fightDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/getAmountFights")
    public ResponseEntity<?> getAmountsFights(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String jwt = header.substring(7);
        String userEmail = jwtService.getUserEmail(jwt);
        return ResponseEntity.ok(fightService.getAmountsFights(userEmail));

    }

    @GetMapping("/get-fight/{username}")
    public ResponseEntity<?> getFight(@PathVariable final String username, @RequestParam(name = "quantity") Integer quantity, @RequestParam(name = "offset") Integer offset, HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String jwt = header.substring(7);
        String userEmail = jwtService.getUserEmail(jwt);
        try {
            return ResponseEntity.ok(fightService.getFight(username, quantity, offset, userEmail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
