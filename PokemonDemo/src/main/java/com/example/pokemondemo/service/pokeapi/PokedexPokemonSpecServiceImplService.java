package com.example.pokemondemo.service.pokeapi;

import com.example.pokemondemo.model.dto.pokeapi.AbilitiesDTO;
import com.example.pokemondemo.model.dto.pokeapi.SingleEsPokemonDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.pokemondemo.model.Constants.POKEMON_SPECIES_URL;
import static com.example.pokemondemo.model.Constants.POKEMON_URL;

@Service
public class PokedexPokemonSpecServiceImplService implements PokedexPokemonSpecsService {


    @Override
    public SingleEsPokemonDTO getEsPokemon(int id, String language) {
        try {
            URL connection = new URL(POKEMON_SPECIES_URL + id);
            HttpURLConnection con = (HttpURLConnection) connection.openConnection();
            con.setRequestMethod("GET");
            String response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();

            // Parse the response into a JSON object.
            JSONObject jsonObject = new JSONObject(response);
            SingleEsPokemonDTO pokemonDTO = new SingleEsPokemonDTO();
            JSONArray names = (JSONArray) jsonObject.get("names");
            for (Object ob : names){
                JSONObject name = (JSONObject) ob;
                JSONObject lang = (JSONObject) name.get("language");
                String lng = (String) lang.get("name");
                if(lng.equals(language)){
                    pokemonDTO.setName((String) name.get("name"));
                    break;
                }
            }
            pokemonDTO.setIndex(id-1);
            pokemonDTO.setLanguage(language);
            JSONArray descriptions = (JSONArray) jsonObject.get("flavor_text_entries");
            for (Object ob : descriptions){
                JSONObject description = (JSONObject) ob;
                JSONObject lang = (JSONObject) description.get("language");
                String lng = (String) lang.get("name");
                if(lng.equals(language)){
                    pokemonDTO.setDescription((String) description.get("flavor_text"));
                    break;
                }
            }

            //stats
            connection = new URL(POKEMON_URL + id);
            con = (HttpURLConnection) connection.openConnection();
            con.setRequestMethod("GET");
            response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            jsonObject = new JSONObject(response);
            JSONArray stats = (JSONArray) jsonObject.get("stats");
            pokemonDTO.setStats(new HashMap<String,Integer>());
            for(Object ob : stats){
                JSONObject stat = (JSONObject) ob;
                JSONObject stat2 = (JSONObject) stat.get("stat");
                String name = (String) stat2.get("name");
                Integer base_stat = (Integer) stat.get("base_stat");
                pokemonDTO.getStats().put(name,base_stat);
            }
            //image
            JSONObject sprites = (JSONObject) jsonObject.get("sprites");
            JSONObject other = (JSONObject) sprites.get("other");
            JSONObject dream_world = (JSONObject) other.get("dream_world");
            pokemonDTO.setImg_path((String) dream_world.get("front_default"));
            //type
            JSONArray types = (JSONArray) jsonObject.get("types");
            pokemonDTO.setType(new ArrayList<String>());
            for (Object ob : types){
                JSONObject type = (JSONObject) ob;
                JSONObject type2 = (JSONObject) type.get("type");
                pokemonDTO.getType().add((String) type2.get("name"));
            }
            //abilities
            JSONArray abilities = (JSONArray) jsonObject.get("abilities");
            pokemonDTO.setAbilities(new ArrayList<AbilitiesDTO>());
            for (Object ob : abilities){
                JSONObject ability = (JSONObject) ob;
                JSONObject ability2 = (JSONObject) ability.get("ability");
                AbilitiesDTO abilitiesDTO = new AbilitiesDTO();
                abilitiesDTO.setName((String) ability2.get("name"));
                String ability_URL = (String) ability2.get("url");
                connection = new URL(ability_URL);
                con = (HttpURLConnection) connection.openConnection();
                con.setRequestMethod("GET");
                response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
                JSONObject jsonObject1 = new JSONObject(response);
                JSONArray flavor_entries = (JSONArray) jsonObject1.get("flavor_text_entries");
                for (Object ob1 : flavor_entries){
                    JSONObject flavor_entry = (JSONObject) ob1;
                    JSONObject lang = (JSONObject) flavor_entry.get("language");
                    String lng = (String) lang.get("name");
                    if(lng.equals(language)){
                        abilitiesDTO.setDescription((String) flavor_entry.get("flavor_text"));
                        break;
                    }
                }
                pokemonDTO.getAbilities().add(abilitiesDTO);
            }


            return pokemonDTO;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SingleEsPokemonDTO getEsPokemon(String name, String language) {
        try {
            HttpURLConnection con = null;
            URL connection = null;
            try{
            connection = new URL(POKEMON_SPECIES_URL + name);
            con = (HttpURLConnection) connection.openConnection();
            con.setRequestMethod("GET");

            }catch (FileNotFoundException e){
                throw new FileNotFoundException("The pokemon does not exist");
            }
            String response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();

            // Parse the response into a JSON object.
            JSONObject jsonObject = new JSONObject(response);
            SingleEsPokemonDTO pokemonDTO = new SingleEsPokemonDTO();
            JSONArray names = (JSONArray) jsonObject.get("names");
            for (Object ob : names){
                JSONObject Name = (JSONObject) ob;
                JSONObject lang = (JSONObject) Name.get("language");
                String lng = (String) lang.get("name");
                if(lng.equals(language)){
                    pokemonDTO.setName((String) Name.get("name"));
                    break;
                }
            }
            pokemonDTO.setIndex((int) jsonObject.get("id")-1);
            pokemonDTO.setLanguage(language);
            JSONArray descriptions = (JSONArray) jsonObject.get("flavor_text_entries");
            for (Object ob : descriptions){
                JSONObject description = (JSONObject) ob;
                JSONObject lang = (JSONObject) description.get("language");
                String lng = (String) lang.get("name");
                if(lng.equals(language)){
                    pokemonDTO.setDescription((String) description.get("flavor_text"));
                    break;
                }
            }

            //stats
            connection = new URL(POKEMON_URL + name);
            con = (HttpURLConnection) connection.openConnection();
            con.setRequestMethod("GET");
            response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            jsonObject = new JSONObject(response);
            JSONArray stats = (JSONArray) jsonObject.get("stats");
            pokemonDTO.setStats(new HashMap<String,Integer>());
            for(Object ob : stats){
                JSONObject stat = (JSONObject) ob;
                JSONObject stat2 = (JSONObject) stat.get("stat");
                String Name = (String) stat2.get("name");
                Integer base_stat = (Integer) stat.get("base_stat");
                pokemonDTO.getStats().put(name,base_stat);
            }
            //image
            JSONObject sprites = (JSONObject) jsonObject.get("sprites");
            JSONObject other = (JSONObject) sprites.get("other");
            JSONObject dream_world = (JSONObject) other.get("dream_world");
            pokemonDTO.setImg_path((String) dream_world.get("front_default"));
            //type
            JSONArray types = (JSONArray) jsonObject.get("types");
            pokemonDTO.setType(new ArrayList<String>());
            for (Object ob : types){
                JSONObject type = (JSONObject) ob;
                JSONObject type2 = (JSONObject) type.get("type");
                pokemonDTO.getType().add((String) type2.get("name"));
            }
            //abilities
            JSONArray abilities = (JSONArray) jsonObject.get("abilities");
            pokemonDTO.setAbilities(new ArrayList<AbilitiesDTO>());
            for (Object ob : abilities){
                JSONObject ability = (JSONObject) ob;
                JSONObject ability2 = (JSONObject) ability.get("ability");
                AbilitiesDTO abilitiesDTO = new AbilitiesDTO();
                abilitiesDTO.setName((String) ability2.get("name"));
                String ability_URL = (String) ability2.get("url");
                connection = new URL(ability_URL);
                con = (HttpURLConnection) connection.openConnection();
                con.setRequestMethod("GET");
                response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
                JSONObject jsonObject1 = new JSONObject(response);
                JSONArray flavor_entries = (JSONArray) jsonObject1.get("flavor_text_entries");
                for (Object ob1 : flavor_entries){
                    JSONObject flavor_entry = (JSONObject) ob1;
                    JSONObject lang = (JSONObject) flavor_entry.get("language");
                    String lng = (String) lang.get("name");
                    if(lng.equals(language)){
                        abilitiesDTO.setDescription((String) flavor_entry.get("flavor_text"));
                        break;
                    }
                }
                pokemonDTO.getAbilities().add(abilitiesDTO);
            }


            return pokemonDTO;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
