package com.example.pokemondemo.controller.rest;

import com.example.pokemondemo.model.authorization.LogInRequest;
import com.example.pokemondemo.model.authorization.SingUpRequest;
import com.example.pokemondemo.model.authorization.LogOutResponse;
import com.example.pokemondemo.service.UserService;
import com.example.pokemondemo.service.JWTService;
import com.example.pokemondemo.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private final UserService userService;
    private final JWTService jwtService;

    public UserResource(final UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }
    @GetMapping("/get-user")
    public ResponseEntity<?> getuser(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String userEmail = jwtService.getUserEmail(token);
        return ResponseEntity.ok(userService.getuser(userEmail));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUpUser(@RequestBody SingUpRequest singUpRequest) {
        try {
            return ResponseEntity.ok(userService.signUpUser(singUpRequest));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> logInUser(@RequestBody LogInRequest logInRequest) {

        try {
            return ResponseEntity.ok(userService.logInUser(logInRequest));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> LogOutUser(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        LogOutResponse logOutResponse = userService.logOutUser(token);

        if (logOutResponse == null)
            return ResponseEntity.unprocessableEntity().body(
                    new StringFormattedMessage("User is not Logged"));
        return ResponseEntity.ok(logOutResponse);
    }


}
