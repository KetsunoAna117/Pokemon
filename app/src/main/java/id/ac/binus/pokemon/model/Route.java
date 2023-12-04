package id.ac.binus.pokemon.model;

import java.util.Vector;

public class Route {
    private String routeName;
    private Vector<String> areaPokemonList;

    public Route(String routeName, Vector<String> areaPokemonList) {
        this.routeName = routeName;
        this.areaPokemonList = areaPokemonList;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Vector<String> getAreaPokemonList() {
        return areaPokemonList;
    }
}
