package me.nathanp.magiccreatures.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Creature extends Types.Typed {

    public static Creature fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Creature.class);
    }

    private String name;
    private int active;
    private ArrayList<String> cards;
    private ArrayList<String> traits;
    private int hp;
    private int hpMax;
    private int spd;
    private int spdMax;
    private int def;
    private int defMax;
    private int atk;
    private int atkMax;
    private int mag;
    private int magMax;

    public void setName(String name) {
        this.name = name;
    }

    public void setType1(int type1) {
        this.type1 = type1;
    }

    public void setType2(int type2) {
        this.type2 = type2;
    }

    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
    }

    public void setSpdMax(int spdMax) {
        this.spdMax = spdMax;
    }

    public void setDefMax(int defMax) {
        this.defMax = defMax;
    }

    public void setAtkMax(int atkMax) {
        this.atkMax = atkMax;
    }

    public void setMagMax(int magMax) {
        this.magMax = magMax;
    }

    public String getName() {
        return name;
    }

    public int getActive() {
        return active;
    }

    public ArrayList<String> getTraits() {
        return traits;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public int getHp() {
        return hp;
    }

    public int getHpMax() {
        return hpMax;
    }

    public int getSpd() {
        return spd;
    }

    public int getSpdMax() {
        return spdMax;
    }

    public int getDef() {
        return def;
    }

    public int getDefMax() {
        return defMax;
    }

    public int getAtk() {
        return atk;
    }

    public int getAtkMax() {
        return atkMax;
    }

    public int getMag() {
        return mag;
    }

    public int getMagMax() {
        return magMax;
    }

    public Creature() {
        this.traits = new ArrayList<>();
        this.cards = new ArrayList<>();
    }

    public Creature(String name, int active, ArrayList<String> traits, ArrayList<String> cards, int type1, int type2, int hp, int hpMax, int spd, int spdMax, int def, int defMax, int atk, int atkMax, int mag, int magMax) {
        this.name = name;
        this.active = active;
        this.traits = traits;
        this.cards = cards;
        this.type1 = type1;
        this.type2 = type2;
        this.hp = hp;
        this.hpMax = hpMax;
        this.spd = spd;
        this.spdMax = spdMax;
        this.def = def;
        this.defMax = defMax;
        this.atk = atk;
        this.atkMax = atkMax;
        this.mag = mag;
        this.magMax = magMax;
    }

    void addCard(String card) {
        cards.add(card);
    }

    void removeCard(String card) {
        cards.remove(card);
    }

    void addTrait(String trait) {
        traits.add(trait);
    }

    void removeTrait(String trait) {
        traits.remove(trait);
    }

    public int getDrawableId() {
        return CreatureResources.getCreatureResource(type1, type2);
    }

    public String getStatSummary() {
        return "hp: " + hpMax + "\nspd: " + spd + ", atk: " + atk + ", def: " + def + ", mag: " + mag;
    }

    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }
}
