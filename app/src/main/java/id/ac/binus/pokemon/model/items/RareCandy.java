package id.ac.binus.pokemon.model.items;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.controller.BackpackController;
import id.ac.binus.pokemon.model.Pokemon;

public class RareCandy extends Item{

    public RareCandy(Integer quantity) {
        super(4, "Rare Candy", "This item will increase pokemon level by 1 and increase all stats by 2", quantity, R.drawable.rare_candy);
    }

    @Override
    public Boolean useItem(Pokemon pokemon) {
        // Rare candy will increase pokemon level by 1 and increase all stats by 2
        pokemon.setLevel(pokemon.getLevel() + 1);
        pokemon.setAttackStats(pokemon.getAttackStats() + 2);
        pokemon.setMaxHp(pokemon.getMaxHp() + 2);
        BackpackController.pokemonUpdate("Rare Candy");
        return true;
    }

    @Override
    public Item clone() {
        return new RareCandy(getQuantity());
    }
}
