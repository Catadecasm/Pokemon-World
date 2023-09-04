package com.example.pokemondemo.rest;

import com.example.pokemondemo.model.DataBase.MechanismDTO;
import com.example.pokemondemo.service.app.MechanismService;
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
@RequestMapping(value = "/api/mechanisms", produces = MediaType.APPLICATION_JSON_VALUE)
public class MechanismResource {

    private final MechanismService mechanismService;

    public MechanismResource(final MechanismService mechanismService) {
        this.mechanismService = mechanismService;
    }

    @GetMapping
    public ResponseEntity<List<MechanismDTO>> getAllMechanisms() {
        return ResponseEntity.ok(mechanismService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MechanismDTO> getMechanism(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(mechanismService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMechanism(
            @RequestBody @Valid final MechanismDTO mechanismDTO) {
        final Integer createdId = mechanismService.create(mechanismDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateMechanism(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final MechanismDTO mechanismDTO) {
        mechanismService.update(id, mechanismDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMechanism(@PathVariable(name = "id") final Integer id) {
        mechanismService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
