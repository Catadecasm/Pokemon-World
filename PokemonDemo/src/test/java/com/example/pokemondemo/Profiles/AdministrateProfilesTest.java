package com.example.pokemondemo.Profiles;

import com.example.pokemondemo.entity.User;
import com.example.pokemondemo.exception.NotFoundException;
import com.example.pokemondemo.model.dto.ChangeRoleDTO;
import com.example.pokemondemo.model.dto.ClassicResponseDTO;
import com.example.pokemondemo.repository.UserRepository;
import com.example.pokemondemo.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AdministrateProfilesTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    private static ChangeRoleDTO changeRoleDTO;

    @BeforeAll
    public static void setUp() {
        changeRoleDTO = ChangeRoleDTO.builder()
                .action("enable")
                .role("ADMIN")
                .username("willy")
                .build();
    }

    @Test
    public void AdministrateProfilesTestExceptions() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.changeRole("willy@endava.com", changeRoleDTO));
        assertThat(exception.getMessage()).isEqualTo("You don't have not provided adequate credentials to access this resource");

        changeRoleDTO.setUsername("notRealUser");
        exception = assertThrows(NotFoundException.class, () -> userService.changeRole("admin@endava.com", changeRoleDTO));
        assertThat(exception.getMessage()).isEqualTo("The trainer does not exist");

        changeRoleDTO.setUsername("willy");
        changeRoleDTO.setRole("TRAINER");
        exception = assertThrows(NotFoundException.class, () -> userService.changeRole("admin@endava.com", changeRoleDTO));
        assertThat(exception.getMessage()).isEqualTo("The trainer already has this role");

        changeRoleDTO.setRole("superAdmin");
        exception = assertThrows(NotFoundException.class, () -> userService.changeRole("admin@endava.com", changeRoleDTO));
        assertThat(exception.getMessage()).isEqualTo("The role does not exist");

    }

    @Test
    public void AdministrateProfilesTestShouldReturnOk() {
        changeRoleDTO.setRole("ADMIN");
        changeRoleDTO.setAction("enable");
        changeRoleDTO.setUsername("willy");

        ClassicResponseDTO response = userService.changeRole("admin@endava.com", changeRoleDTO);
        assertThat(response.getResponseCode()).isEqualTo("OK");
        assertThat(response.getResponseMessage()).isEqualTo("Role updated successfully");
        User user = userRepository.findByUsernameIgnoreCase(changeRoleDTO.getUsername());
        assertThat(user.getRole().toString()).isEqualTo(changeRoleDTO.getRole());

        //Normalize
        changeRoleDTO.setRole("TRAINER");
        changeRoleDTO.setAction("enable");
        changeRoleDTO.setUsername("willy");
        userService.changeRole("admin@endava.com", changeRoleDTO);

    }


}
