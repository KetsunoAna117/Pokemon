package id.ac.binus.pokemon.controller;

public class Helper {
    public static Integer getRandomNumber(Integer min, Integer max){
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
