package id.ac.binus.pokemon.model.items;

import id.ac.binus.pokemon.model.Pokemon;

public class HpUp extends Item{
    public HpUp(String name, String desc) {
        super(name, desc);
    }

    @Override
    public void useItem(Pokemon pokemon) {
        // HPUp will increase pokemon max HP stats by 1
        pokemon.setMaxHp(pokemon.getMaxHp() + 1);
    }
}
