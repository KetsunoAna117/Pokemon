package id.ac.binus.pokemon.model.items;

import id.ac.binus.pokemon.model.Pokemon;

public class Protein extends Item{
    public Protein(String name, String desc) {
        super(name, desc);
    }

    @Override
    public void useItem(Pokemon pokemon) {
        // Protein will increase pokemon atk stats by 1
        pokemon.setAttackStats(pokemon.getAttackStats() + 1);
    }
}
