package com.example.pokemondemo.PokeConnection;

import com.example.pokemondemo.model.DataBase.PokemonDTO;

import com.example.pokemondemo.model.PokeApi.AbilitiesDTO;
import com.example.pokemondemo.model.PokeApi.EvolutionChainDTO;
import com.example.pokemondemo.model.PokeApi.SingleEsPokemonDTO;
import com.example.pokemondemo.model.PokeApi.SinglePokemonDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PokeApi implements Connection{

    String url = "https://pokeapi.co/api/v2/pokemon/";

    public static void main(String[] args) {
        PokeApi pokeApi = new PokeApi();
        pokeApi.getEvolutionChain(133,"en");
    }

    @Override
    public SinglePokemonDTO getPokemon(int id) {
        try {
            URL connection = new URL(url + id);
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
            for (Object ob : types){
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

    @Override
    public SinglePokemonDTO getPokemon(String name) {

        try {
            URL connection = new URL(url + name);
            HttpURLConnection con = (HttpURLConnection) connection.openConnection();
            con.setRequestMethod("GET");
            String response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            //System.out.println(response);
/*
            // Parse the response into a JSON object.
            JSONObject jsonObject = new JSONObject(response);
            SinglePokemonDTO pokemonDTO = new SinglePokemonDTO();
            pokemonDTO.setId((int) jsonObject.get("id"));
            JSONArray types = (JSONArray) jsonObject.get("types");
            */

            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SingleEsPokemonDTO getEsPokemon(int id, String language) {
        url = "https://pokeapi.co/api/v2/pokemon-species/";
        try {
            URL connection = new URL(url + id);
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
            url = "https://pokeapi.co/api/v2/pokemon/";
            connection = new URL(url + id);
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
                url = (String) ability2.get("url");
                connection = new URL(url);
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
            JSONObject prueba = new JSONObject();
            prueba.put("abilities",pokemonDTO.getAbilities());
            prueba.put("stats",pokemonDTO.getStats());
            prueba.put("type",pokemonDTO.getType());
            prueba.put("description",pokemonDTO.getDescription());
            prueba.put("img_path",pokemonDTO.getImg_path());
            prueba.put("language",pokemonDTO.getLanguage());
            prueba.put("index",pokemonDTO.getIndex());
            prueba.put("name",pokemonDTO.getName());

            System.out.println(prueba.toString());


            return pokemonDTO;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SingleEsPokemonDTO getEsPokemon(String name, String language) {
        return null;
    }

    @Override
    public EvolutionChainDTO getEvolutionChain(int id, String language) {
        try {
            url = "https://pokeapi.co/api/v2/pokemon-species/";
            URL connection = new URL(url + id);
            HttpURLConnection con = (HttpURLConnection) connection.openConnection();
            con.setRequestMethod("GET");
            String response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            System.out.println(response);
            // Parse the response into a JSON object.
            JSONObject jsonObject = new JSONObject(response);
            JSONObject evolution_chain = (JSONObject) jsonObject.get("evolution_chain");
            url = (String) evolution_chain.get("url");
            connection = new URL(url);
            con = (HttpURLConnection) connection.openConnection();
            con.setRequestMethod("GET");
            response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            jsonObject = new JSONObject(response);
            EvolutionChainDTO evolutionChainDTO = new EvolutionChainDTO();




        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public EvolutionChainDTO getEvolutionChain(String name, String language) {
        try {
            url = "https://pokeapi.co/api/v2/evolution-chain/";
            URL connection = new URL(url + name);
            HttpURLConnection con = (HttpURLConnection) connection.openConnection();
            con.setRequestMethod("GET");
            String response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();

            // Parse the response into a JSON object.
            JSONObject jsonObject = new JSONObject(response);
            System.out.println(jsonObject.toString());

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
