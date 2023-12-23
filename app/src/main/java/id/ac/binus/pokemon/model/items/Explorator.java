package id.ac.binus.pokemon.model.items;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.model.Pokemon;


public class Explorator extends Item {
    public Explorator(Integer quantity) {
        super(6, "Explorator", "This item allows you to discover new routes", quantity, R.drawable.explorer_kit);
    }

    @Override
    public Boolean useItem(Pokemon pokemon) {
        return true;
    }

    @Override
    public Item clone() {
        return new Explorator(getQuantity());
    }
}
