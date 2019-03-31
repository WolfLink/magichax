package me.nathanp.magiccreatures.model;

import java.util.List;

public class Player {

    private String username;
    private String currentGame;
    private boolean online;
    Player(){}

    public String getUsername() {
        return username;
    }

    public String getCurrentGame() {
        return currentGame;
    }

    public boolean isOnline() {
        return online;
    }
}
