package com.example.pokemondemo.repository;


import com.example.pokemondemo.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Integer> {

    boolean existsByName(Integer name);

}
