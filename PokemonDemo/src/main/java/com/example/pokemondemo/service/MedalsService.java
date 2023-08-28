package com.example.pokemondemo.service;


import com.example.pokemondemo.domain.Medals;
import com.example.pokemondemo.domain.User;
import com.example.pokemondemo.model.MedalsDTO;
import com.example.pokemondemo.repository.MedalsRepository;
import com.example.pokemondemo.repository.UserRepository;
import com.example.pokemondemo.util.*;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MedalsService {

    private final MedalsRepository medalsRepository;
    private final UserRepository userRepository;

    public MedalsService(final MedalsRepository medalsRepository,
            final UserRepository userRepository) {
        this.medalsRepository = medalsRepository;
        this.userRepository = userRepository;
    }

    public List<MedalsDTO> findAll() {
        final List<Medals> medalss = medalsRepository.findAll(Sort.by("id"));
        return medalss.stream()
                .map(medals -> mapToDTO(medals, new MedalsDTO()))
                .toList();
    }

    public MedalsDTO get(final Integer id) {
        return medalsRepository.findById(id)
                .map(medals -> mapToDTO(medals, new MedalsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MedalsDTO medalsDTO) {
        final Medals medals = new Medals();
        mapToEntity(medalsDTO, medals);
        return medalsRepository.save(medals).getId();
    }

    public void update(final Integer id, final MedalsDTO medalsDTO) {
        final Medals medals = medalsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(medalsDTO, medals);
        medalsRepository.save(medals);
    }

    public void delete(final Integer id) {
        medalsRepository.deleteById(id);
    }

    private MedalsDTO mapToDTO(final Medals medals, final MedalsDTO medalsDTO) {
        medalsDTO.setId(medals.getId());
        medalsDTO.setTitle(medals.getTitle());
        medalsDTO.setUser(medals.getUser() == null ? null : medals.getUser().getId());
        return medalsDTO;
    }

    private Medals mapToEntity(final MedalsDTO medalsDTO, final Medals medals) {
        medals.setTitle(medalsDTO.getTitle());
        final User user = medalsDTO.getUser() == null ? null : userRepository.findById(medalsDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        medals.setUser(user);
        return medals;
    }

    public boolean titleExists(final String title) {
        return medalsRepository.existsByTitleIgnoreCase(title);
    }

}
