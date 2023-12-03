package id.ac.binus.pokemon.model.pokemon_attribute;

import com.google.gson.annotations.SerializedName;

public class TypeName {
    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }
}
