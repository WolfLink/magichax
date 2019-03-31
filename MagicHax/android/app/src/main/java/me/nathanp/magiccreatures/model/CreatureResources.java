package me.nathanp.magiccreatures.model;

import me.nathanp.magiccreatures.R;

public class CreatureResources {
    private static final int[] fire     = {R.drawable.creature_fire};
    private static final int[] water    = {R.drawable.creature_firewater,    R.drawable.creature_water};
    private static final int[] earth    = {R.drawable.creature_fireearth,    R.drawable.creature_waterearth,    R.drawable.creature_earth};
    private static final int[] air      = {R.drawable.creature_fireair,      R.drawable.creature_waterair,      R.drawable.creature_earthair,      R.drawable.creature_air};
    private static final int[] plant    = {R.drawable.creature_fireplant,    R.drawable.creature_waterplant,    R.drawable.creature_earthplant,    R.drawable.creature_plantair,    R.drawable.creature_plant};
    private static final int[] electric = {R.drawable.creature_fireelectric, R.drawable.creature_waterelectric, R.drawable.creature_electricearth, R.drawable.creature_electricair, R.drawable.creature_electricplant, R.drawable.creature_electric};

    private static final int[][] drawables = {fire, water, earth, air, plant, electric};

    public static int getCreatureResource(int type1, int type2) {
        if (type1 >= type2) return drawables[type1][type2];
        else return drawables[type2][type1];
    }
}
