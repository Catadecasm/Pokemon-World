package com.example.pokemondemo.PokeConnection;

import com.example.pokemondemo.model.DataBase.PokemonDTO;

import com.example.pokemondemo.model.PokeApi.SingleEsPokemonDTO;
import com.example.pokemondemo.model.PokeApi.SinglePokemonDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PokeApi implements Connection{

    String url = "https://pokeapi.co/api/v2/pokemon/";

    public static void main(String[] args) {
        PokeApi pokeApi = new PokeApi();
        System.out.println(pokeApi.getEsPokemon(1,"fr"));
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
                String lng = (String) name.get("name");
                if(lng.equals(language)){
                    pokemonDTO.setName((String) name.get("name"));
                    break;
                }

            }



            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SingleEsPokemonDTO getEsPokemon(String name, String language) {
        return null;
    }


}
