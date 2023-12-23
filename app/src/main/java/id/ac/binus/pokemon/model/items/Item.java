package id.ac.binus.pokemon.model.items;

import androidx.annotation.NonNull;

import id.ac.binus.pokemon.model.Pokemon;

public abstract class Item {
    private Integer id;
    private Integer icon;
    private String name;
    private String desc;
    private Integer quantity;

    public Item(Integer id, String name, String desc, Integer quantity, Integer icon) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.desc = desc;
        this.icon = icon;
    }

    public abstract Boolean useItem(Pokemon pokemon);
    public Boolean useItem(){
        return true;
    }
    public abstract Item clone();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
