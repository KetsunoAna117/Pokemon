package id.ac.binus.pokemon.model.items;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.BackpackController;
import id.ac.binus.pokemon.model.Pokemon;

public class Revive extends Item {


    public Revive(Integer quantity) {
        super(5, "Revive", "This item will revive fainted pokemon. Can't be used on non-fainted pokemon", quantity, R.drawable.revive);
    }

    @Override
    public Boolean useItem(Pokemon pokemon) {
        if(pokemon.getHp() <= 0){
            // Revive will revive fainted pokemon
            pokemon.setHp(pokemon.getMaxHp());
            BackpackController.pokemonUpdate("Revive");
            return true;
        }
        return false;
    }

    @Override
    public Item clone() {
        return new Revive(getQuantity());
    }
}
