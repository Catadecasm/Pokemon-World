package com.example.pokemondemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Hello Pokemon World!";
    }

}
