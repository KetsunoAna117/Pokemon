package id.ac.binus.pokemon.model.items;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.BackpackController;
import id.ac.binus.pokemon.model.Pokemon;

public class Protein extends Item{


    public Protein(Integer quantity) {
        super(3, "Protein", "This item will increase pokemon attack stats by 1", quantity, R.drawable.protein);
    }

    @Override
    public Boolean useItem(Pokemon pokemon) {
        // Protein will increase pokemon atk stats by 1
        pokemon.setAttackStats(pokemon.getAttackStats() + 1);
        BackpackController.pokemonUpdate("Protein");
        return true;
    }

    @Override
    public Item clone() {
        return new Protein(getQuantity());
    }
}
