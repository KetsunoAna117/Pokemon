package id.ac.binus.pokemon.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import id.ac.binus.pokemon.model.Pokemon;
import id.ac.binus.pokemon.model.items.HpUp;
import id.ac.binus.pokemon.model.items.Item;
import id.ac.binus.pokemon.model.items.Potion;
import id.ac.binus.pokemon.model.items.Protein;
import id.ac.binus.pokemon.model.items.RareCandy;
import id.ac.binus.pokemon.model.items.Revive;

public class BackpackController {
    private static Vector<Item> backpack;
    public static Vector<Item> getTrainerBackpackData(Integer trainerId){
        // TODO ADD SQL TO GET BACKPACK ITEM BY TRAINER ID
        init();

        return backpack;
    }

    // TODO this is just for mockup data, delete this later.
    private static Boolean initFlag = false;
    private static void init(){
        if(!initFlag){
            backpack = new Vector<>();
            Item potion = new Potion(99);
            Item protein = new Protein(99);
            Item rareCandy = new RareCandy( 5);
            Item hpup = new HpUp(99);
            Item revive = new Revive(10);

            backpack.add(potion);
            backpack.add(protein);
            backpack.add(rareCandy);
            backpack.add(hpup);
            backpack.add(revive);

            initFlag = true;
        }
    }

    public static Integer getItemIndexFromBackpack(Item selectedItem){
        Integer index = -1;
        for(int i=0; i<backpack.size(); i++){
            if(backpack.get(i).equals(selectedItem)){
                return i;
            }
        }

        return index;
    }

    public static Boolean useItem(Item selectedItem, Pokemon selectedPokemon){
        Integer index = getItemIndexFromBackpack(selectedItem);

        if(index != -1 && selectedItem.getQuantity() > 0){
            Boolean statusIsTrue = selectedItem.useItem(selectedPokemon);
            if(statusIsTrue){
                selectedItem.setQuantity(selectedItem.getQuantity() - 1);
                return true;
            }
            return false;
        }
        return false;
    }

    public static Item getItemReward(){
        Integer dropItemId = Helper.getRandomNumber(0, backpack.size() - 1);
        for(Item i: backpack){
            if(i.getId().equals(dropItemId)){
                i.setQuantity(i.getQuantity() + 1);
                return i;
            }
        }
        return null;
    }
}
