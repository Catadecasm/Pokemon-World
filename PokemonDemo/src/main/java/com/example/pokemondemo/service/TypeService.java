package com.example.pokemondemo.service;


import java.util.List;

import com.example.pokemondemo.domain.Pokemon;
import com.example.pokemondemo.util.*;
import com.example.pokemondemo.domain.Type;
import com.example.pokemondemo.model.TypeDTO;
import com.example.pokemondemo.repository.PokemonRepository;
import com.example.pokemondemo.repository.TypeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TypeService {

    private final TypeRepository typeRepository;
    private final PokemonRepository pokemonRepository;

    public TypeService(final TypeRepository typeRepository,
            final PokemonRepository pokemonRepository) {
        this.typeRepository = typeRepository;
        this.pokemonRepository = pokemonRepository;
    }

    public List<TypeDTO> findAll() {
        final List<Type> types = typeRepository.findAll(Sort.by("id"));
        return types.stream()
                .map(type -> mapToDTO(type, new TypeDTO()))
                .toList();
    }

    public TypeDTO get(final Integer id) {
        return typeRepository.findById(id)
                .map(type -> mapToDTO(type, new TypeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final TypeDTO typeDTO) {
        final Type type = new Type();
        mapToEntity(typeDTO, type);
        return typeRepository.save(type).getId();
    }

    public void update(final Integer id, final TypeDTO typeDTO) {
        final Type type = typeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(typeDTO, type);
        typeRepository.save(type);
    }

    public void delete(final Integer id) {
        typeRepository.deleteById(id);
    }

    private TypeDTO mapToDTO(final Type type, final TypeDTO typeDTO) {
        typeDTO.setId(type.getId());
        typeDTO.setName(type.getName());
        typeDTO.setPokemonid(type.getPokemonid() == null ? null : type.getPokemonid().getId());
        return typeDTO;
    }

    private Type mapToEntity(final TypeDTO typeDTO, final Type type) {
        type.setName(typeDTO.getName());
        final Pokemon pokemonid = typeDTO.getPokemonid() == null ? null : pokemonRepository.findById(typeDTO.getPokemonid())
                .orElseThrow(() -> new NotFoundException("pokemonid not found"));
        type.setPokemonid(pokemonid);
        return type;
    }

}
