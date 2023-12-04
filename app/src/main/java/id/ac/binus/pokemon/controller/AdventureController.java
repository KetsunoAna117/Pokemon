package id.ac.binus.pokemon.controller;

import java.util.Vector;

import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.Route;

public class AdventureController{
    private static Route activeRoute = null;
    private static Pokemon enemyPokemon;

    public static Route getActiveRoute() {
        return activeRoute;
    }

    public static void setActiveRoute(Route activeRoute) {
        AdventureController.activeRoute = activeRoute;
    }

    public static Vector<Route> getAllRoutes(){
        // TODO Add SQL to get Routes saved in database
        Vector<Route> routes = new Vector<Route>();

        Vector<String> pokemonList = new Vector<String>();
        pokemonList.add("charmander");
        pokemonList.add("squirtle");
        pokemonList.add("bulbasaur");

        routes.add(new Route("Route 1", 1, 10, pokemonList));

        pokemonList = new Vector<String>();
        pokemonList.add("rayquaza");
        pokemonList.add("arceus");

        routes.add(new Route("Route 2", 20, 50, pokemonList));

        return routes;
    }

    public static void findEnemy(OnPokemonLoadedListener onPokemonLoadedListener){
        if(enemyPokemon == null){
            Integer randomPokemonIndex = Helper.getRandomNumber(0, getActiveRoute().getAreaPokemonList().size() - 1);
            PokeApiService.getPokemonByName(getActiveRoute().getAreaPokemonList().get(randomPokemonIndex), onPokemonLoadedListener);
        }
    }

    public static Pokemon getEnemyPokemon() {
        return enemyPokemon;
    }

    public static void setEnemyPokemon(Pokemon enemyPokemon) {
        AdventureController.enemyPokemon = enemyPokemon;
    }

    // MOCKUP DATA
//    private void getRoute1Pokemon(){
//        // RESET
//        retrievedPokemon = new Vector<>();
//        getPokemonAPICounter = 1;
//        maxPokemonAPICounter = 3;
//
//        // Get all Pokemon from API
//        PokeApiService.getPokemonByName("bulbasaur", this);
//        PokeApiService.getPokemonByName("charmander", this);
//        PokeApiService.getPokemonByName("squirtle", this);
//
//        while(true){
//            if(getPokemonAPICounter >= maxPokemonAPICounter){
//                routes.add(new Route("Route 1", retrievedPokemon));
//                break;
//            }
//        }
//
//    }
//
//    private void getRoute2Pokemon(){
//        // RESET
//        retrievedPokemon = new Vector<>();
//        getPokemonAPICounter = 1;
//        maxPokemonAPICounter = 2;
//
//        // Get all Pokemon from API
//        PokeApiService.getPokemonByName("rayquaza", this);
//        PokeApiService.getPokemonByName("arceus", this);
//
//        while(true){
//            if(getPokemonAPICounter >= maxPokemonAPICounter){
//                routes.add(new Route("Route 2", retrievedPokemon));
//                break;
//            }
//        }
//    }

}
