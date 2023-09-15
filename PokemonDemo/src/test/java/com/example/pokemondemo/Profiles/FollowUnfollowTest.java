package com.example.pokemondemo.Profiles;

import com.example.pokemondemo.exception.NotFoundException;
import com.example.pokemondemo.model.dto.ClassicResponseDTO;
import com.example.pokemondemo.service.FollowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FollowUnfollowTest {

    @Autowired
    FollowService followService;


    @Test
    public void followExceptions() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> followService.follow("willy@endava.com", "notrealUser"));
        assertThat(exception.getMessage()).isEqualTo("The trainer does not exist");

        exception = assertThrows(NotFoundException.class, () -> followService.follow("willy@endava.com", "willy"));
        assertThat(exception.getMessage()).isEqualTo("You can't follow yourself");

        exception = assertThrows(NotFoundException.class, () -> followService.follow("andy@endava.com", "willy"));
        assertThat(exception.getMessage()).isEqualTo("You are already following this trainer");

    }
    @Test
    public void unfollowExceptions(){
        NotFoundException exception = assertThrows(NotFoundException.class, () -> followService.unfollow("willy@endava.com","notrealUser"));
        assertThat(exception.getMessage()).isEqualTo("The trainer does not exist");

        exception = assertThrows(NotFoundException.class, () -> followService.unfollow("willy@endava.com","satu"));
        assertThat(exception.getMessage()).isEqualTo("The trainer is not followed by you");
    }

    @Test
    public void followShouldReturnIdFollow(){
        ClassicResponseDTO response = followService.follow("willy@endava.com", "andy");
        assertThat(response.getResponseCode()).isEqualTo("OK");
        assertThat(response.getResponseMessage()).isEqualTo("willy is now following andy");

        // Unfollow
        response = followService.unfollow("willy@endava.com","andy");
        assertThat(response.getResponseCode()).isEqualTo("OK");
        assertThat(response.getResponseMessage()).isEqualTo("willy is not following andy anymore");
    }

    @Test
    public void followShouldSucceed() {
        ClassicResponseDTO response = followService.follow("willy@endava.com", "andy");
        assertThat(response.getResponseCode()).isEqualTo("OK");
        assertThat(response.getResponseMessage()).isEqualTo("willy is now following andy");
    }

    @Test
    public void followDuplicateShouldThrowException() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> followService.follow("willy@endava.com", "andy"));
        assertThat(exception.getMessage()).isEqualTo("You are already following this trainer");
    }
}
