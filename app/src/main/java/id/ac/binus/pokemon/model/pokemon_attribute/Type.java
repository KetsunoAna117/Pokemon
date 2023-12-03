package id.ac.binus.pokemon.model.pokemon_attribute;

import com.google.gson.annotations.SerializedName;

public class Type {
    @SerializedName("type")
    private TypeName typeName;

    public TypeName getTypeName() {
        return typeName;
    }
}
