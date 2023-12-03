package id.ac.binus.pokemon.controller;

import id.ac.binus.pokemon.model.Pokemon;

public interface OnPokemonLoadedListener {
    void onPokemonReceived(Pokemon pokemon);
}
