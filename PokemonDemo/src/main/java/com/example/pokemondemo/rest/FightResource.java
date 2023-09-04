package com.example.pokemondemo.rest;

import com.example.pokemondemo.model.DataBase.FightDTO;
import com.example.pokemondemo.service.app.FightService;
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
@RequestMapping(value = "/api/fights", produces = MediaType.APPLICATION_JSON_VALUE)
public class FightResource {

    private final FightService fightService;

    public FightResource(final FightService fightService) {
        this.fightService = fightService;
    }

    @GetMapping
    public ResponseEntity<List<FightDTO>> getAllFights() {
        return ResponseEntity.ok(fightService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FightDTO> getFight(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(fightService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createFight(@RequestBody @Valid final FightDTO fightDTO) {
        final Integer createdId = fightService.create(fightDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
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
