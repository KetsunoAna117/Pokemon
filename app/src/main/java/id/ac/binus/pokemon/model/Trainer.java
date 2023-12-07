package id.ac.binus.pokemon.model;

import java.util.LinkedList;
import java.util.Vector;

import id.ac.binus.pokemon.model.items.Item;

public class Trainer {
    private Integer trainedId;
    private String name;
    private Integer profilePicture;
    private Integer level;
    private String gender;
    private Integer exp;
    private Integer baseExp;
    private Pokemon activePokemon;
    private LinkedList<Pokemon> party;
    private Vector<Item> backpack;

    public Trainer(String name, String gender, Integer profilePicture, Integer level) {
        this.trainedId = 1;
        this.name = name;
        this.gender = gender;
        this.profilePicture = profilePicture;
        this.level = level;
        this.exp = 0;
        this.baseExp = 20;
        this.backpack = new Vector<>();
        this.party = new LinkedList<>();
    }

    public Pokemon getActivePokemon() {
        return activePokemon;
    }

    public void setActivePokemon(Pokemon activePokemon) {
        this.activePokemon = activePokemon;
    }

    public Integer getTrainedId() {
        return trainedId;
    }

    public void setTrainedId(Integer trainedId) {
        this.trainedId = trainedId;
    }

    public Integer getBaseExp() {
        return baseExp;
    }

    public void setBaseExp(Integer baseExp) {
        this.baseExp = baseExp;
    }

    public Integer getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Integer profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public LinkedList<Pokemon> getParty() {
        return party;
    }

    public Integer getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Vector<Item> getBackpack() {
        return backpack;
    }

}
