package com.example.pokemondemo.PokeConnection.Impl;

import com.example.pokemondemo.PokeConnection.Intf.EvolutionChain;
import com.example.pokemondemo.model.PokeApi.EvolutionChainDTO;
import com.example.pokemondemo.model.PokeApi.EvolutionDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static com.example.pokemondemo.PokeConnection.URL.Constants.*;

@Service
public class EvolutionChainService implements EvolutionChain {

    private void CreateChain(JSONArray evolves_to, EvolutionChainDTO evolutionChainDTO) {
        JSONObject species = new JSONObject();
        String specie = "";
        if (evolves_to.length() == 0) {
            return;
        }
        for (Object ob : evolves_to) {
            EvolutionDTO evolutionDTO = new EvolutionDTO();
            JSONObject evolution = (JSONObject) ob;
            species = (JSONObject) evolution.get("species");
            specie = (String) species.get("name");
            evolutionDTO.setName(specie);
            evolutionDTO.setDetailed_url(POKEDEX_DETAILED_URL + evolutionDTO.getName());
            evolutionChainDTO.getChain().add(evolutionDTO);
            if (evolution.has("evolves_to")) {
                JSONArray next_evolves_to = (JSONArray) evolution.get("evolves_to");
                CreateChain(next_evolves_to, evolutionChainDTO);
            }

        }
    }

    private EvolutionDTO LookNextEvolution(JSONObject evolves_to, String name) {
        JSONObject species = evolves_to.getJSONObject("species");
        if (name.equals(species.getString("name")) && evolves_to.has("evolves_to")) {
            JSONArray next_evolve = evolves_to.getJSONArray("evolves_to");
            if (next_evolve.length() > 0) {
                JSONObject next_evolution = next_evolve.getJSONObject(0);
                JSONObject next_species = next_evolution.getJSONObject("species");
                EvolutionDTO return_value = new EvolutionDTO();
                return_value.setName(next_species.getString("name"));
                return_value.setDetailed_url(POKEDEX_DETAILED_URL + return_value.getName());
                return return_value;
            }
            return new EvolutionDTO();

        }

            for (Object ob : evolves_to.getJSONArray("evolves_to")) {
                return LookNextEvolution((JSONObject) ob, name);
            }
        return new EvolutionDTO();

    }

    @Override
    public EvolutionChainDTO getEvolutionChain(int id, String language) {

        try {
            URL connection = new URL(POKEMON_SPECIES_URL + id);

            HttpURLConnection con = (HttpURLConnection) connection.openConnection();
            con.setRequestMethod("GET");
            String response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();

            // Parse the response into a JSON object.
            JSONObject jsonObject = new JSONObject(response);
            String name = (String) jsonObject.get("name");
            JSONObject evolution_chain = (JSONObject) jsonObject.get("evolution_chain");
            String evolution_URL = (String) evolution_chain.get("url");
            connection = new URL(evolution_URL);
            con = (HttpURLConnection) connection.openConnection();
            con.setRequestMethod("GET");

            response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            jsonObject = new JSONObject(response);
            EvolutionDTO evolutionDTO = new EvolutionDTO();
            EvolutionChainDTO evolutionChainDTO = new EvolutionChainDTO();
            JSONObject chain = (JSONObject) jsonObject.get("chain");
            JSONObject species = (JSONObject) chain.get("species");
            evolutionDTO.setName((String) species.get("name"));
            evolutionDTO.setDetailed_url(POKEDEX_DETAILED_URL + evolutionDTO.getName());
            evolutionChainDTO.getChain().add(evolutionDTO);
            JSONArray evolves_to = (JSONArray) chain.get("evolves_to");

            CreateChain(evolves_to, evolutionChainDTO);
            evolutionChainDTO.setNext_evol(LookNextEvolution(chain,name));
            for (EvolutionDTO e : evolutionChainDTO.getChain()) {
                connection = new URL(POKEMON_SPECIES_URL+ e.getName());
                con = (HttpURLConnection) connection.openConnection();
                con.setRequestMethod("GET");
                response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
                jsonObject = new JSONObject(response);
                JSONArray names = (JSONArray) jsonObject.get("names");
                for (Object ob : names) {
                    JSONObject name_object = (JSONObject) ob;
                    JSONObject language_object = (JSONObject) name_object.get("language");
                    if (language_object.get("name").equals(language)) {
                        e.setName((String) name_object.get("name"));
                    }
                }
            }
            if(!evolutionChainDTO.getNext_evol().getName().isEmpty()){
                connection = new URL(POKEMON_SPECIES_URL+ evolutionChainDTO.getNext_evol().getName());
                con = (HttpURLConnection) connection.openConnection();
                con.setRequestMethod("GET");
                response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
                jsonObject = new JSONObject(response);
                JSONArray names = (JSONArray) jsonObject.get("names");
                for (Object ob : names) {
                    JSONObject name_object = (JSONObject) ob;
                    JSONObject language_object = (JSONObject) name_object.get("language");
                    if (language_object.get("name").equals(language)) {
                        evolutionChainDTO.getNext_evol().setName((String) name_object.get("name"));
                    }
                }
            }
            return evolutionChainDTO;


        } catch (MalformedURLException e) {
            System.out.println("Malformed URL Exception");
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("IO Exception");
            throw new RuntimeException(e);
        }
    }

    @Override
    public EvolutionChainDTO getEvolutionChain(String name, String language) {
        try {


            URL connection = new URL(POKEMON_SPECIES_URL + name);

            HttpURLConnection con = (HttpURLConnection) connection.openConnection();
            con.setRequestMethod("GET");
            String response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();

            // Parse the response into a JSON object.
            JSONObject jsonObject = new JSONObject(response);
            JSONObject evolution_chain = (JSONObject) jsonObject.get("evolution_chain");
            String evolution_URL = (String) evolution_chain.get("url");
            connection = new URL(evolution_URL);
            con = (HttpURLConnection) connection.openConnection();
            con.setRequestMethod("GET");

            response = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            jsonObject = new JSONObject(response);
            EvolutionDTO evolutionDTO = new EvolutionDTO();
            EvolutionChainDTO evolutionChainDTO = new EvolutionChainDTO();
            JSONObject chain = (JSONObject) jsonObject.get("chain");
            JSONObject species = (JSONObject) chain.get("species");
            evolutionDTO.setName((String) species.get("name"));
            evolutionDTO.setDetailed_url(POKEDEX_DETAILED_URL + evolutionDTO.getName());
            evolutionChainDTO.getChain().add(evolutionDTO);
            JSONArray evolves_to = (JSONArray) chain.get("evolves_to");

            CreateChain(evolves_to, evolutionChainDTO);
            evolutionChainDTO.setNext_evol(LookNextEvolution(chain,name));
            return evolutionChainDTO;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
