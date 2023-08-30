package com.example.pokemondemo.security.service;


import com.example.pokemondemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        boolean userExists = userRepository.existsByUsernameIgnoreCase(username.toUpperCase());

        if (!userExists) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Since you're checking for existence, there is no need to retrieve the user object.
        // You can return a UserDetails object with minimal information, like an empty password and no authorities.
        return new org.springframework.security.core.userdetails.User(username, "", new ArrayList<>());
    }

}
