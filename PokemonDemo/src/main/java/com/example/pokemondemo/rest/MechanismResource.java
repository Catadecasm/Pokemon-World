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



}
