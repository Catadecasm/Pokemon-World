package com.example.pokemondemo.authentication;

import com.example.pokemondemo.exception.NotFoundException;
import com.example.pokemondemo.model.authorization.LogOutResponse;
import com.example.pokemondemo.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional 
class LogoutTest {

    @Autowired
    UserService userService;

    @Test
    void testLogOutUser_Success() {
        // Arrange
        String validToken = "validToken";

        // Act
        LogOutResponse logOutResponse = userService.logOutUser(validToken);

        // Assert
        assertThat(logOutResponse).isNotNull();
        assertThat(logOutResponse.getStatus()).isEqualTo("ok"); // Utiliza getStatus() en lugar de getMessage()
    }

    @Test
    void testLogOutUser_UserNotLoggedIn() {
        // Arrange
        String invalidToken = "invalidToken";

        // Act & Assert
        NotFoundException exception = Assertions.<NotFoundException>assertThrows(NotFoundException.class, () -> userService.logOutUser(invalidToken));
        assertThat(exception.getMessage()).isEqualTo("User not found");
    }

}
