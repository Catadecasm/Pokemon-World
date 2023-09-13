package com.example.pokemondemo.authentication;

import com.example.pokemondemo.exception.NotFoundException;
import com.example.pokemondemo.model.authorization.SingUpRequest;
import com.example.pokemondemo.model.authorization.SingUpResponse;
import com.example.pokemondemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class SignupTest {

    @Autowired
    private UserService userService;

    @Test
    void Validate_Email() {
        SingUpRequest singUpRequest = SingUpRequest.builder()
                .username("willy")
                .email("")
                .role("TRAINER")
                .password("123456")
                .build();
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.signUpUser(singUpRequest));
        assertThat(exception.getMessage()).isEqualTo("The email is not valid");
    }

    @Test
    void Validate_Role() {
        SingUpRequest singUpRequest = SingUpRequest.builder()
                .username("willy")
                .email("hola@endava.com")
                .role("TRINER")
                .password("123456")
                .build();
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.signUpUser(singUpRequest));
        assertThat(exception.getMessage()).isEqualTo("The role does not exist");
    }

    @Test
    void Validate_Unique() {
        SingUpRequest singUpRequest = SingUpRequest.builder()
                .username("willy")
                .email("hola@endava.com")
                .role("TRAINER")
                .password("123456")
                .build();
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.signUpUser(singUpRequest));
        assertThat(exception.getMessage()).isEqualTo("The username or email already exists");
    }

    @Test
    void Verify_functionality() {
        SingUpRequest singUpRequest = SingUpRequest.builder()
                .username("willy2")
                .email("hola@endava.com")
                .role("doctor")
                .password("123456")
                .build();
        SingUpResponse singUpResponse = userService.signUpUser(singUpRequest);
        assertThat(singUpResponse).isNotNull();
        assertThat(singUpResponse.getEmail()).isEqualTo(singUpRequest.getEmail());
        assertThat(singUpResponse.getUsername()).isEqualTo(singUpRequest.getUsername());
        assertThat(singUpResponse.getId()).isGreaterThan(0);
    }
}
