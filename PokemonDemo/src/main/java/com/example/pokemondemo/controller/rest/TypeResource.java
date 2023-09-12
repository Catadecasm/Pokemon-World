package com.example.pokemondemo.controller.rest;

import com.example.pokemondemo.service.TypeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/types", produces = MediaType.APPLICATION_JSON_VALUE)
public class TypeResource {

    private final TypeService typeService;

    public TypeResource(final TypeService typeService) {
        this.typeService = typeService;
    }

}
