package id.ac.binus.pokemon.controller;

import android.util.Log;

import id.ac.binus.pokemon.model.Pokemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokeApiService {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";
    private static IPokeApi pokeApiInterface;

    public static IPokeApi getPokeApiInterface() {
        if (pokeApiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            pokeApiInterface = retrofit.create(IPokeApi.class);
        }
        return pokeApiInterface;
    }

    public static void getWildPokemonDataFromAPIByName(String name, OnPokemonLoadedListener listener){
        Call<Pokemon> requestPokemon = PokeApiService.getPokeApiInterface().getPokemonByNationalDexId(name);
        requestPokemon.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if(response.isSuccessful() && response.body() != null){
                    Pokemon pokemon = new Pokemon(response.body().getPokemonId(),
                                                    response.body().getName(),
                                                    response.body().getTypes(), response.body().getSprites(),
                                                    AdventureController.getActiveRoute().getMinLevel(), AdventureController.getActiveRoute().getMaxLevel());
                    Log.d("DEBUG", "pokemon sprites from api event: " + response.body().getSprites().getFrontSprite());
                    listener.onPokemonReceived(pokemon);
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("API Error", "Error");
            }
        });
    }
    public static void getCapturedPokemonDataFromAPIByName(String name, OnPokemonLoadedListener listener, Integer level, Integer maxHp, Integer attackStats){
        Log.d("DEBUG", "try call pokemon from api event");
        Call<Pokemon> requestPokemon = PokeApiService.getPokeApiInterface().getPokemonByNationalDexId(name);
        requestPokemon.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                Log.d("DEBUG", "success call pokemon from api event");
                if(response.isSuccessful() && response.body() != null){
                    Pokemon pokemon = new Pokemon(response.body().getPokemonId(),
                            response.body().getName(),
                            response.body().getTypes(),
                            level,
                            response.body().getSprites(),
                            maxHp,
                            attackStats);
                    Log.d("DEBUG", "pokemon sprites from api event: " + response.body().getSprites().getFrontSprite());
                    listener.onPokemonReceived(pokemon);
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("API Error", "Error");
            }
        });
    }

    public static void getStarterPokemonDataFromAPIByName(String name, OnPokemonLoadedListener listener, Integer level, Integer maxHp, Integer attackStats){
        Log.d("DEBUG", "try call starter pokemon from api event");
        Call<Pokemon> requestPokemon = PokeApiService.getPokeApiInterface().getPokemonByNationalDexId(name);
        requestPokemon.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                Log.d("DEBUG", "success call pokemon from api event");
                if(response.isSuccessful() && response.body() != null){
                    Pokemon pokemon = new Pokemon(response.body().getPokemonId(),
                            response.body().getName(),
                            response.body().getTypes(),
                            level,
                            response.body().getSprites(),
                            maxHp,
                            attackStats);
                    Log.d("DEBUG", "pokemon sprites from api event: " + response.body().getSprites().getFrontSprite());
                    listener.onStarterReceived(pokemon);
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("API Error", "Error");
            }
        });
    }
}
