package id.ac.binus.pokemon.listener;

import id.ac.binus.pokemon.model.Pokemon;

public interface OnPokemonLoadedListener {
    void onPokemonReceived(Pokemon pokemon);
    void onStarterReceived(Pokemon pokemon);
}
