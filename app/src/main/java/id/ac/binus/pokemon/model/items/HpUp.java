package id.ac.binus.pokemon.model.items;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.BackpackController;
import id.ac.binus.pokemon.model.Pokemon;

public class HpUp extends Item{

    public HpUp(Integer quantity) {
        super(1, "Hp Up", "This item will increase pokemon max hp stats by 1", quantity, R.drawable.hp_up);
    }

    @Override
    public Boolean useItem(Pokemon pokemon) {
        // HPUp will increase pokemon max HP stats by 1
        pokemon.setMaxHp(pokemon.getMaxHp() + 1);
        BackpackController.pokemonUpdate("Hp Up");
        return true;
    }

    @Override
    public Item clone() {
        return new HpUp(getQuantity());
    }
}
