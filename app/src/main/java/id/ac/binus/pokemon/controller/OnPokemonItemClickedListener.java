package id.ac.binus.pokemon.controller;

import id.ac.binus.pokemon.model.items.Item;

public interface OnPokemonItemClickedListener {
    void onItemClickedEvent(Item selectedItem);
}
