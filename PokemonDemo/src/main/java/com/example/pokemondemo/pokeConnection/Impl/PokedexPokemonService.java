package com.example.pokemondemo.pokeConnection.Impl;

import com.example.pokemondemo.pokeConnection.Intf.PokedexPokemon;
import com.example.pokemondemo.model.PokeApi.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.pokemondemo.pokeConnection.URL.Constants.POKEMON_URL;

@Service
public class PokedexPokemonService implements PokedexPokemon {

    public PokedexDashboardDTO getPokedexDashboard(int offset, int quantity) {
        PokedexDashboardDTO pokedexDashboardDTO = new PokedexDashboardDTO();
        pokedexDashboardDTO.setQuantity(quantity);
        pokedexDashboardDTO.setId(offset);
        pokedexDashboardDTO.setResult(new ArrayList<SinglePokemonDTO>());
        Integer id = offset * quantity + 1;
        for (int i = 0; i < quantity; i++, id++) {
            pokedexDashboardDTO.getResult().add(getPokemon(id));
        }
        return pokedexDashboardDTO;
    }

    @Override
    public SinglePokemonDTO getPokemon(int id) {
        try {
            URL connection = new URL(POKEMON_URL + id);
            System.out.println(connection.toString());
            HttpURLConnection con = (HttpURLConnection) connection.openConnection();
            con.setRequestMethod("GET");
            String response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();


            // Parse the response into a JSON object.
            JSONObject jsonObject = new JSONObject(response);
            SinglePokemonDTO pokemonDTO = new SinglePokemonDTO();
            pokemonDTO.setName((String) jsonObject.get("name"));
            pokemonDTO.setId((int) jsonObject.get("id"));
            JSONArray types = (JSONArray) jsonObject.get("types");
            pokemonDTO.setTypes(new ArrayList<String>());
            for (Object ob : types) {
                JSONObject type = (JSONObject) ob;
                JSONObject type2 = (JSONObject) type.get("type");
                pokemonDTO.getTypes().add((String) type2.get("name"));
            }
            JSONObject sprites = (JSONObject) jsonObject.get("sprites");
            JSONObject other = (JSONObject) sprites.get("other");
            JSONObject dream_world = (JSONObject) other.get("dream_world");
            pokemonDTO.setImg_path((String) dream_world.get("front_default"));


            return pokemonDTO;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
