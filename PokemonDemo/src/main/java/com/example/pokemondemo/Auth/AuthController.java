package com.example.pokemondemo.Auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping(value = "login")
    public String login()
    {
        return "Login From Public Endpoint";
    }

    @PostMapping(value = "register")
    public String register()
    {
        return "Register From public endpoint";
    }
}