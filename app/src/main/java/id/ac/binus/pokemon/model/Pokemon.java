package id.ac.binus.pokemon.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Vector;

import id.ac.binus.pokemon.model.pokemon_attribute.Sprites;
import id.ac.binus.pokemon.model.pokemon_attribute.Type;

public class Pokemon implements Cloneable {
    @SerializedName("id")
    private Integer pokemonId;
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
    public Pokemon(Integer pokemonId, String name, List<Type> types, Sprites sprites, Integer level) {
        this.name = name;
        this.level = level;
        this.sprites = sprites;
        this.types = types;

        // Should get Randomized HP and attack from 1 to pokemon current level
        this.maxHp = (int) (Math.random() * level) + 1;
        this.hp = maxHp;
        this.attackStats = (int) (Math.random() * level) + 1;;
    }

    // To make an existing pokemon data
    public Pokemon(Integer pokemonId, String name, List<Type> types, Integer level, Sprites sprites, Integer hp, Integer maxHp, Integer attackStats) {
        this.pokemonId = pokemonId;
        this.name = name;
        this.types = types;
        this.level = level;
        this.sprites = sprites;
        this.hp = hp;
        this.maxHp = maxHp;
        this.attackStats = attackStats;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public List<Type> getTypes() {
        return types;
    }

    public Integer getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(Integer pokemonId) {
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