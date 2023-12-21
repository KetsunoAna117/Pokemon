package id.ac.binus.pokemon.model;

import java.util.Vector;

public class Route {
    // Table 1
    private String routeName;
    private Integer minLevel;
    private Integer maxLevel;
    private Vector<String> areaPokemonList; // table 2

    public Route(String routeName, Integer minLevel, Integer maxLevel, Vector<String> areaPokemonList) {
        this.routeName = routeName;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.areaPokemonList = areaPokemonList;
    }

    public Integer getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Integer minLevel) {
        this.minLevel = minLevel;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
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
