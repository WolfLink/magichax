package me.nathanp.magiccreatures.model;

import com.google.firebase.database.Exclude;

public class Trait extends Types.Typed {
    public String name;
    public int cost;
    public String description;

    @Exclude
    private boolean selected = false;

    public void setSelected(boolean b) {
        selected = b;
    }

    public boolean isSelected() {
        return selected;
    }
}
