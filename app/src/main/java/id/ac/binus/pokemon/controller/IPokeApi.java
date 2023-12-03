package id.ac.binus.pokemon.controller;

import id.ac.binus.pokemon.model.Pokemon;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IPokeApi {
    @GET("pokemon/{pokemonDexId}")
    Call<Pokemon> getPokemonByNationalDexId(@Path("pokemonDexId") String pokemonDexId);

    @GET("pokemon/{pokemonName}")
    Call<Pokemon> getPokemonByName(@Path("pokemonName") String pokemonName);
}
