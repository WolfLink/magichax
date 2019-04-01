package me.nathanp.magiccreatures.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "creature_cache_table")
public class Creature {

    public static class Creatures {
        Creatures() {}
        public Creature c1;
        public Creature c2;
        public Creature c3;
    }

    public static Creature fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Creature.class);
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private int active;
    private ArrayList<String> cards;
    private int type1;
    private int type2;
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getActive() {
        return active;
    }

    public ArrayList<String> getCards() {
        return cards;
    }

    public int getType1() {
        return type1;
    }

    public int getType2() {
        return type2;
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

    @Ignore
    Creature() {}

    public Creature(String name, int active, ArrayList<String> cards, int type1, int type2, int hp, int hpMax, int spd, int spdMax, int def, int defMax, int atk, int atkMax, int mag, int magMax) {
        this.name = name;
        this.active = active;
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

    public void setId(int id) {
        this.id = id;
    }

    void addCard(String card) {
        cards.add(card);
    }

    void removeCard(String card) {
        cards.remove(card);
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
