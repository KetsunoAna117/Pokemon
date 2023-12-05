package id.ac.binus.pokemon.controller;

import id.ac.binus.pokemon.model.Pokemon;

public interface OnPokemonButtonSwitchListener {
    void onPokemonSwitchButtonClick(Pokemon selectedPokemon);
    void onPokemonReleaseButtonClick(Pokemon selectedPokemon);
}
