package id.ac.binus.pokemon.model.items;

import id.ac.binus.pokemon.model.Pokemon;

public class RareCandy extends Item{
    public RareCandy(String name, String desc) {
        super(name, desc);
    }

    @Override
    public void useItem(Pokemon pokemon) {
        // Rare candy will increase pokemon level by 1 and increase all stats by 2
        pokemon.setLevel(pokemon.getLevel() + 1);
        pokemon.setAttackStats(pokemon.getAttackStats() + 2);
        pokemon.setMaxHp(pokemon.getMaxHp() + 2);
    }
}
