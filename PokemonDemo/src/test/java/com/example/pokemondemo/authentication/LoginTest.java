package com.example.pokemondemo.authentication;

import com.example.pokemondemo.entity.User;
import com.example.pokemondemo.exception.NotFoundException;
import com.example.pokemondemo.model.authorization.LogInRequest;
import com.example.pokemondemo.model.authorization.LogInResponse;
import com.example.pokemondemo.repository.UserRepository;
import com.example.pokemondemo.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class LoginTest {

    @Autowired
    UserService userService;

    @Test
    void Validate_Multiple_Session() {
        LogInRequest logInRequest = LogInRequest.builder()
                .email("hola@endava.com")
                .password("123456")
                .build();
        LogInResponse logInResponse =  userService.logInUser(logInRequest);
        assertThat(logInResponse).isNotNull();
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.logInUser(logInRequest));
        assertThat(exception.getMessage()).isEqualTo("The user is already logged");
    }
    @Test
    void Validate_User_Not_Found() {
        LogInRequest logInRequest = LogInRequest.builder()
                .email("hola@endava.com")
                .password("12346")
                .build();

        Exception exception = assertThrows(Exception.class, () -> userService.logInUser(logInRequest));
        assertThat(exception.getMessage()).isEqualTo("Bad credentials");
    }

    @Test
    void Validate_Invalid_Password() {
        LogInRequest logInRequest = LogInRequest.builder()
                .email("hola@endava.com")
                .password("123467")
                .build();
        Exception exception = assertThrows(Exception.class, () -> userService.logInUser(logInRequest));
        assertThat(exception.getMessage()).isEqualTo("Bad credentials");
    }

    

    @AfterAll
    public static void cleanUp(@Autowired UserRepository userRepository) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setLogged(false);
            userRepository.save(user);
        }
    }

}
