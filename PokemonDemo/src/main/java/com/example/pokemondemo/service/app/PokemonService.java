package com.example.pokemondemo.service.app;


import java.util.ArrayList;
import java.util.List;

import com.example.pokemondemo.domain.*;
import com.example.pokemondemo.model.DataBase.PokemonDTO;
import com.example.pokemondemo.model.PokeApi.AbilitiesDTO;
import com.example.pokemondemo.model.PokeApi.SingleEsPokemonDTO;
import com.example.pokemondemo.model.payload.request.MechanismsChangeDTO;
import com.example.pokemondemo.model.payload.response.ClassicResponse;
import com.example.pokemondemo.model.payload.response.TrainerPokedex;
import com.example.pokemondemo.pokeConnection.Impl.PokedexPokemonSpecService;
import com.example.pokemondemo.repository.*;
import com.example.pokemondemo.service.security.JWTService;
import com.example.pokemondemo.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PokedexPokemonSpecService pokedexPokemonSpecService;
    private final MechanismRepository mechanismRepository;
    private final TypeRepository typeRepository;

    public PokemonService(final PokemonRepository pokemonRepository,
                          final UserRepository userRepository, FollowRepository followRepository, JWTService jwtService, PokedexPokemonSpecService pokedexPokemonSpecService, MechanismRepository mechanismRepository, TypeRepository typeRepository) {
        this.pokemonRepository = pokemonRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.pokedexPokemonSpecService = pokedexPokemonSpecService;
        this.mechanismRepository = mechanismRepository;
        this.typeRepository = typeRepository;
    }

    public ClassicResponse curePokemon(String userEmail, Integer pokemonId, String username) {
        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        if (user.getRole().equals(Role.DOCTOR)) {
            User trainer = userRepository.findByUsernameIgnoreCase(username);
            if (trainer == null) {
                throw new NotFoundException("The trainer does not exist");
            }
            Pokemon pokemon = pokemonRepository.findById(pokemonId).get();
            if (pokemon.getUser().equals(trainer)) {
                return ClassicResponse.builder()
                        .ResponseCode("200")
                        .ResponseMessage("You have cured the " + pokemon.getName() + " of " + trainer.getRealUsername())
                        .build();
            }
            throw new NotFoundException("The pokemon does not belong to the trainer");
        }
        throw new NotFoundException("You don't have not provided adequate credentials to access this resource");
    }

    public TrainerPokedex findAllByFollow(String userEmail, String FollowedUser, Integer quantity, Integer offset) {

        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        User followed = userRepository.findByUsernameIgnoreCase(FollowedUser);

        if (followed == null) {
            throw new NotFoundException("The trainer does not exist");
        }
        if (!user.getRole().equals(Role.PROFESSOR)) {
            if (!user.getRealUsername().equals(followed.getRealUsername())){
                Follow follow = followRepository.findFollowByFollowedAndFollower(followed, user);
                if (follow == null) {
                    throw new NotFoundException("You don't follow this pokémon trainer.");
                }

            }
        }
        return findAllByUser(followed.getEmail(), quantity, offset);

    }


    private TrainerPokedex findAllByUser(String userEmail, Integer quantity, Integer offset) {
        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        Pageable pageable = PageRequest.of(offset, quantity);
        List<Pokemon> pokemons = pokemonRepository.findAllByUser(user, pageable);
        return TrainerPokedex.builder()
                .quantity(quantity)
                .index(offset)
                .result(pokemons.stream().map(pokemon -> MapToDTO(pokemon, new PokemonDTO())).toList())
                .build();
    }

    private PokemonDTO MapToDTO(Pokemon pokemon, PokemonDTO pokemonDTO) {
        pokemonDTO.setId(pokemon.getId());
        pokemonDTO.setName(pokemon.getName());
        pokemonDTO.setSpecie(pokemon.getSpecie());
        ArrayList<Type> types = (ArrayList<Type>) typeRepository.findAllByPokemonid(pokemon);
        pokemonDTO.setType(types.stream().map(Type::getName).toList());
        return pokemonDTO;
    }

    public ClassicResponse addNewPokemon(String userEmail, PokemonDTO pokemonDTO, String username) {
        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        if (!user.getRealUsername().equals(username)) {
            throw new NotFoundException("You can't add a pokemon to other trainer");
        }
        SingleEsPokemonDTO pokemon = null;
        try {
            pokemon = pokedexPokemonSpecService.getEsPokemon(pokemonDTO.getSpecie(), "en");
        } catch (Exception e) {
            throw new NotFoundException("The pokemon does not exist");

        }
        Pokemon pokemonSave = Pokemon.builder()
                .name(pokemonDTO.getName())
                .specie(pokemon.getName())
                .user(user)
                .image(pokemon.getImg_path())
                .build();
        pokemonSave = pokemonRepository.save(pokemonSave);

        for (AbilitiesDTO a : pokemon.getAbilities()) {
            if (!mechanismRepository.findByNameIgnoreCase(a.getName())) {
                Mechanism mechanism = Mechanism.builder()
                        .name(a.getName())
                        .pokemonid(pokemonSave)
                        .build();
                mechanismRepository.save(mechanism);
            } else if (!mechanismRepository.findByNameIgnoreCaseAndPokemonid(a.getName(), pokemonSave)) {
                Mechanism mechanism = Mechanism.builder()
                        .name(a.getName())
                        .pokemonid(pokemonSave)
                        .build();
                mechanismRepository.save(mechanism);
            }
        }
        for (String t : pokemon.getType()) {
            if (!typeRepository.existsByNameIgnoreCase(t)) {
                Type type = Type.builder()
                        .name(t)
                        .pokemonid(pokemonSave)
                        .build();
                typeRepository.save(type);
            } else if (!typeRepository.existsByNameIgnoreCaseAndPokemonid(t, pokemonSave)) {
                Type type = Type.builder()
                        .name(t)
                        .pokemonid(pokemonSave)
                        .build();
                typeRepository.save(type);
            }
        }
        return ClassicResponse.builder()
                .ResponseCode("OK")
                .ResponseMessage("The pokemon " + pokemonDTO.getName() + " added to " + user.getRealUsername() + " with id " + pokemonSave.getId())
                .build();
    }

    public ClassicResponse updatePokemon(String userEmail, PokemonDTO pokemonDTO, String username) {
        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        if (!user.getRealUsername().equals(username)) {
            throw new NotFoundException("You can't update a pokemon to other trainer");
        }
        if (pokemonRepository.findById(pokemonDTO.getId()).isEmpty()) {
            throw new NotFoundException("The pokemon does not exist, the id does not match with any pokemon");
        }
        Pokemon pokemon = pokemonRepository.findById(pokemonDTO.getId()).get();
        pokemon.setName(pokemonDTO.getName());
        pokemonRepository.save(pokemon);
        return ClassicResponse.builder()
                .ResponseCode("OK")
                .ResponseMessage("The pokemon " + pokemonDTO.getName() + " updated to " + user.getRealUsername())
                .build();
    }

    public ClassicResponse deletePokemon(String userEmail, Integer pokemonId, String username) {
        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        if (!user.getRealUsername().equals(username)) {
            throw new NotFoundException("You can't delete a pokemon to other trainer");
        }
        if (pokemonRepository.findById(pokemonId).isEmpty()) {
            throw new NotFoundException("The pokemon does not exist, the id does not match with any pokemon");
        }
        Pokemon pokemon = pokemonRepository.findById(pokemonId).get();
        pokemonRepository.delete(pokemon);
        return ClassicResponse.builder()
                .ResponseCode("OK")
                .ResponseMessage("The pokemon " + pokemon.getName() + " deleted to " + user.getRealUsername())
                .build();
    }

    public TrainerPokedex getPokemons(String userEmail, Integer quantity, Integer offset, String username) {
        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        if (!user.getRealUsername().equals(username)) {
            throw new NotFoundException("You can't get a pokemon to other trainer, If you want to, use the follow endpoint!");
        }
        return findAllByUser(username, quantity, offset);
    }

    public Boolean updateMecha(String userEmail, MechanismsChangeDTO mechanismsChangeDTO) {
        User user = userRepository.findByEmailIgnoreCase(userEmail).get();
        List<Pokemon> pokemons = pokemonRepository.findAllByUser(user);
        for (Pokemon p : pokemons) {
            if (p.getName().equals(mechanismsChangeDTO.getName())) {
                for (String m : mechanismsChangeDTO.getMechanism()) {
                    if (!mechanismRepository.findByNameIgnoreCaseAndPokemonid(m, p)) {
                        Mechanism mechanism = Mechanism.builder()
                                .name(m)
                                .pokemonid(p)
                                .build();
                        mechanismRepository.save(mechanism);
                        return true;
                    }

                }
                throw new NotFoundException("The pokemon already has the mechanism");
            }
        }
        throw new NotFoundException("The pokemon does not exist");
    }
}
