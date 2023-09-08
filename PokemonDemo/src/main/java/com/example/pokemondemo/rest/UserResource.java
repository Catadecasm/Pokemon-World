package com.example.pokemondemo.rest;

import com.example.pokemondemo.model.DataBase.UserDTO;
import com.example.pokemondemo.model.authDTO.request.LogInRequest;
import com.example.pokemondemo.model.authDTO.request.SingUpRequest;
import com.example.pokemondemo.model.authDTO.response.LogInResponse;
import com.example.pokemondemo.model.authDTO.response.LogOutResponse;
import com.example.pokemondemo.model.authDTO.response.SingUpResponse;
import com.example.pokemondemo.service.app.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private final UserService userService;

    public UserResource(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUpUser(@RequestBody SingUpRequest singUpRequest) {
        return ResponseEntity.ok(userService.signUpUser(singUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponse> logInUser(@RequestBody LogInRequest logInRequest) {
        return ResponseEntity.ok(userService.logInUser(logInRequest));
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
