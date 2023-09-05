package com.example.pokemondemo.rest;

import com.example.pokemondemo.model.DataBase.FollowDTO;
import com.example.pokemondemo.model.profilePayload.request.ActionDTO;
import com.example.pokemondemo.service.app.FollowService;
import com.example.pokemondemo.service.security.JWTService;
import com.example.pokemondemo.util.NotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping(value = "/api/follows", produces = MediaType.APPLICATION_JSON_VALUE)
public class FollowResource {

    private final FollowService followService;
    private final JWTService jwtService;

    public FollowResource(final FollowService followService, JWTService jwtService) {
        this.followService = followService;
        this.jwtService = jwtService;
    }

    @PostMapping("/{username}/relationship")
    public ResponseEntity<?> followAndUnfollow(@RequestBody ActionDTO msg, HttpServletRequest request, @PathVariable(name = "username") String username) {
        String header = request.getHeader("Authorization");
        String jwt = header.substring(7);
        String userEmail = jwtService.getUserEmail(jwt);
        if (userEmail != null) {
            if (msg.getAction().equals("follow")) {
                try {
                    return ResponseEntity.ok(followService.follow(userEmail, username));
                } catch (NotFoundException e) {
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            } else if (msg.getAction().equals("unfollow")) {
                try {
                    return ResponseEntity.ok(followService.unfollow(userEmail, username));
                } catch (NotFoundException e) {
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            } else {
                return ResponseEntity.badRequest().body("Action not found");
            }
        }
        return ResponseEntity.badRequest().body("User not found");
    }

}
