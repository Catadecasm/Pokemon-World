package com.example.pokemondemo.rest;

import com.example.pokemondemo.model.DataBase.MedalsDTO;
import com.example.pokemondemo.service.app.MedalsService;
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
@RequestMapping(value = "/api/medalss", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedalsResource {

    private final MedalsService medalsService;

    public MedalsResource(final MedalsService medalsService) {
        this.medalsService = medalsService;
    }

    @GetMapping
    public ResponseEntity<List<MedalsDTO>> getAllMedalss() {
        return ResponseEntity.ok(medalsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedalsDTO> getMedals(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(medalsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMedals(@RequestBody @Valid final MedalsDTO medalsDTO) {
        final Integer createdId = medalsService.create(medalsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateMedals(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final MedalsDTO medalsDTO) {
        medalsService.update(id, medalsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMedals(@PathVariable(name = "id") final Integer id) {
        medalsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
