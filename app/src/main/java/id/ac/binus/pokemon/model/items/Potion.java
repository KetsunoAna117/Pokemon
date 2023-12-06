package id.ac.binus.pokemon.model.items;

import id.ac.binus.pokemon.R;
import id.ac.binus.pokemon.model.Pokemon;

public class Potion extends Item{

    public Potion(Integer quantity) {
        super(2, "Potion", "This item will heal pokemon up to 10 HP. Can't be used on fainted pokemon", quantity, R.drawable.potion);
    }

    @Override
    public Boolean useItem(Pokemon pokemon) {
        if(pokemon.getHp() > 0 && pokemon.getHp() < pokemon.getMaxHp()){
            // Potion will heal pokemon up to 10 HP and not overhealed
            if(pokemon.getHp() + 10 > pokemon.getMaxHp()){
                pokemon.setHp(pokemon.getMaxHp());
            }
            else {
                pokemon.setHp(pokemon.getHp() + 10);
            }
            return true;
        }

        return false;
    }

    @Override
    public Item clone() {
        return new Potion(getQuantity());
    }
}
