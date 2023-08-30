package com.example.pokemondemo.service;


import com.example.pokemondemo.domain.League;
import com.example.pokemondemo.model.DataBase.LeagueDTO;
import com.example.pokemondemo.repository.LeagueRepository;
import com.example.pokemondemo.util.*;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;

    public LeagueService(final LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public List<LeagueDTO> findAll() {
        final List<League> leagues = leagueRepository.findAll(Sort.by("id"));
        return leagues.stream()
                .map(league -> mapToDTO(league, new LeagueDTO()))
                .toList();
    }

    public LeagueDTO get(final Integer id) {
        return leagueRepository.findById(id)
                .map(league -> mapToDTO(league, new LeagueDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final LeagueDTO leagueDTO) {
        final League league = new League();
        mapToEntity(leagueDTO, league);
        return leagueRepository.save(league).getId();
    }

    public void update(final Integer id, final LeagueDTO leagueDTO) {
        final League league = leagueRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(leagueDTO, league);
        leagueRepository.save(league);
    }

    public void delete(final Integer id) {
        leagueRepository.deleteById(id);
    }

    private LeagueDTO mapToDTO(final League league, final LeagueDTO leagueDTO) {
        leagueDTO.setId(league.getId());
        leagueDTO.setName(league.getName());
        return leagueDTO;
    }

    private League mapToEntity(final LeagueDTO leagueDTO, final League league) {
        league.setName(leagueDTO.getName());
        return league;
    }

    public boolean nameExists(final String name) {
        return leagueRepository.existsByNameIgnoreCase(name);
    }

}
