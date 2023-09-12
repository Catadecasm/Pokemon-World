package com.example.pokemondemo.service;


import com.example.pokemondemo.entity.Mechanism;
import com.example.pokemondemo.entity.Pokemon;
import com.example.pokemondemo.model.dto.MechanismDTO;
import com.example.pokemondemo.repository.MechanismRepository;
import com.example.pokemondemo.repository.PokemonRepository;
import com.example.pokemondemo.exception.*;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MechanismService {

    private final MechanismRepository mechanismRepository;
    private final PokemonRepository pokemonRepository;

    public MechanismService(final MechanismRepository mechanismRepository,
            final PokemonRepository pokemonRepository) {
        this.mechanismRepository = mechanismRepository;
        this.pokemonRepository = pokemonRepository;
    }

    public List<MechanismDTO> findAll() {
        final List<Mechanism> mechanisms = mechanismRepository.findAll(Sort.by("id"));
        return mechanisms.stream()
                .map(mechanism -> mapToDTO(mechanism, new MechanismDTO()))
                .toList();
    }

    public MechanismDTO get(final Integer id) {
        return mechanismRepository.findById(id)
                .map(mechanism -> mapToDTO(mechanism, new MechanismDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MechanismDTO mechanismDTO) {
        final Mechanism mechanism = new Mechanism();
        mapToEntity(mechanismDTO, mechanism);
        return mechanismRepository.save(mechanism).getId();
    }

    public void update(final Integer id, final MechanismDTO mechanismDTO) {
        final Mechanism mechanism = mechanismRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(mechanismDTO, mechanism);
        mechanismRepository.save(mechanism);
    }

    public void delete(final Integer id) {
        mechanismRepository.deleteById(id);
    }

    private MechanismDTO mapToDTO(final Mechanism mechanism, final MechanismDTO mechanismDTO) {
        mechanismDTO.setId(mechanism.getId());
        mechanismDTO.setName(mechanism.getName());
        mechanismDTO.setPokemonid(mechanism.getPokemonid() == null ? null : mechanism.getPokemonid().getId());
        return mechanismDTO;
    }

    private Mechanism mapToEntity(final MechanismDTO mechanismDTO, final Mechanism mechanism) {
        mechanism.setName(mechanismDTO.getName());
        final Pokemon pokemonid = mechanismDTO.getPokemonid() == null ? null : pokemonRepository.findById(mechanismDTO.getPokemonid())
                .orElseThrow(() -> new NotFoundException("pokemonid not found"));
        mechanism.setPokemonid(pokemonid);
        return mechanism;
    }

}
