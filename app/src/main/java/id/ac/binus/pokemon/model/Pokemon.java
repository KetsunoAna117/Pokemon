package id.ac.binus.pokemon.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Vector;

import id.ac.binus.pokemon.controller.Helper;
import id.ac.binus.pokemon.model.pokemon_attribute.Sprites;
import id.ac.binus.pokemon.model.pokemon_attribute.Type;

public class Pokemon implements Cloneable {
    @SerializedName("id")
    private String pokemonId;
    @SerializedName("name")
    private String name;

    @SerializedName("types")
    private List<Type> types;
    private Integer level;

    @SerializedName("sprites")
    private Sprites sprites;
    private Integer hp;
    private Integer maxHp;
    private Integer attackStats;

    // To initialize random pokemon encounter
    public Pokemon(String pokemonId, String name, List<Type> types, Sprites sprites, Integer minLevel, Integer maxLvl) {
        this.name = name;
        this.level = Helper.getRandomNumber(minLevel, maxLvl);
        this.sprites = sprites;
        this.types = types;

        // Should get Randomized HP and attack from 1 to pokemon current level
        this.maxHp = Helper.getRandomNumber(1, level);
        this.hp = maxHp;
        this.attackStats = Helper.getRandomNumber(1, level);
    }

    // To make an existing pokemon data
    public Pokemon(String pokemonId, String name, List<Type> types, Integer level, Sprites sprites, Integer maxHp, Integer attackStats) {
        this.pokemonId = pokemonId;
        this.name = name;
        this.types = types;
        this.level = level;
        this.sprites = sprites;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.attackStats = attackStats;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public List<Type> getTypes() {
        return types;
    }

    public String getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(String pokemonId) {
        this.pokemonId = pokemonId;
    }

    public Integer getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(Integer maxHp) {
        this.maxHp = maxHp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getAttackStats() {
        return attackStats;
    }

    public void setAttackStats(Integer attackStats) {
        this.attackStats = attackStats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return (Pokemon) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
