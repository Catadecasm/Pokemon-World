package com.example.pokemondemo.rest;

import com.example.pokemondemo.model.payload.request.FightDTO;
import com.example.pokemondemo.service.app.FightService;
import com.example.pokemondemo.service.security.JWTService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

    @GetMapping()
    public ResponseEntity<?> getAllFights() {
        return ResponseEntity.ok(fightService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FightDTO> getFight(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(fightService.get(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateFight(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final FightDTO fightDTO) {
        fightService.update(id, fightDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFight(@PathVariable(name = "id") final Integer id) {
        fightService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
