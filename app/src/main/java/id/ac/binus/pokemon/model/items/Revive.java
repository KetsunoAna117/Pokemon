package id.ac.binus.pokemon.model.items;

import id.ac.binus.pokemon.model.Pokemon;

public class Revive extends Item {

    public Revive(String name, String desc) {
        super(name, desc);
    }

    @Override
    public void useItem(Pokemon pokemon) {
        if(pokemon.getHp() <= 0){
            // Revive will revive fainted pokemon
            pokemon.setHp(pokemon.getMaxHp());
        }
    }
}
