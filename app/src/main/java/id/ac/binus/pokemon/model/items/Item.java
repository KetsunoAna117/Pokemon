package id.ac.binus.pokemon.model.items;

import id.ac.binus.pokemon.model.Pokemon;

public abstract class Item {
    private Integer icon;
    private String name;
    private String desc;

    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public abstract void useItem(Pokemon pokemon);

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
