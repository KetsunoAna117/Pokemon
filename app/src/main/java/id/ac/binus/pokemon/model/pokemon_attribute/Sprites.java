package id.ac.binus.pokemon.model.pokemon_attribute;

import com.google.gson.annotations.SerializedName;

public class Sprites {
    @SerializedName("back_default")
    private String backSprite;
    @SerializedName("front_default")
    private String frontSprite;

    public String getBackSprite() {
        return backSprite;
    }

    public void setBackSprite(String backSprite) {
        this.backSprite = backSprite;
    }

    public String getFrontSprite() {
        return frontSprite;
    }

    public void setFrontSprite(String frontSprite) {
        this.frontSprite = frontSprite;
    }
}
