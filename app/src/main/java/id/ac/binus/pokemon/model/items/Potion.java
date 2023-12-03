package id.ac.binus.pokemon.model.items;

import id.ac.binus.pokemon.model.Pokemon;

public class Potion extends Item{
    public Potion(String name, String desc) {
        super(name, desc);
    }

    @Override
    public void useItem(Pokemon pokemon) {
        if(pokemon.getHp() > 0){
            // Potion will heal pokemon up to 10 HP
            pokemon.setHp(pokemon.getHp() + 10);
        }

        // TODO add alert that pokemon can't be healed if pokemon is fainted
    }
}
