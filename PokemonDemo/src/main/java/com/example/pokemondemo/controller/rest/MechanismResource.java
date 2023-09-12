package com.example.pokemondemo.controller.rest;

import com.example.pokemondemo.service.MechanismService;
import org.springframework.http.MediaType;
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
