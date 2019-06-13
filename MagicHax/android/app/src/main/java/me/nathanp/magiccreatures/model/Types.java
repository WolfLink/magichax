package me.nathanp.magiccreatures.model;

import java.util.HashSet;
import java.util.Set;

public class Types {
    public static int TYPE_ANY = -1;
    public static int TYPE_FIRE = 0;
    public static int TYPE_WATER = 1;
    public static int TYPE_EARTH = 2;
    public static int TYPE_AIR = 3;
    public static int TYPE_PLANT = 4;
    public static int TYPE_ELECTRICITY = 5;

    public static boolean TypedCompare(Typed a, Typed b) {
        Set<Integer> allowed = new HashSet<>();
        allowed.add(TYPE_ANY);
        allowed.add(a.getType1());
        allowed.add(a.getType2());
        return allowed.contains(b.getType1()) && allowed.contains(b.getType2());
    }

    public static abstract class Typed {
        int type1;
        int type2;

        public int getType1() {
            return type1;
        }

        public int getType2() {
            return type2;
        }
    }
}
