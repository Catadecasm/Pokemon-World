package com.example.pokemondemo.rest;

import com.example.pokemondemo.model.DataBase.TypeDTO;
import com.example.pokemondemo.service.app.TypeService;
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
@RequestMapping(value = "/api/types", produces = MediaType.APPLICATION_JSON_VALUE)
public class TypeResource {

    private final TypeService typeService;

    public TypeResource(final TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public ResponseEntity<List<TypeDTO>> getAllTypes() {
        return ResponseEntity.ok(typeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeDTO> getType(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(typeService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createType(@RequestBody @Valid final TypeDTO typeDTO) {
        final Integer createdId = typeService.create(typeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateType(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final TypeDTO typeDTO) {
        typeService.update(id, typeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteType(@PathVariable(name = "id") final Integer id) {
        typeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
