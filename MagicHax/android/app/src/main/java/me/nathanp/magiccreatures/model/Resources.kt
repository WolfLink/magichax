package me.nathanp.magiccreatures.model

import android.util.Log
import me.nathanp.magiccreatures.R

object Resources {
    object Creatures {
        private val any =      intArrayOf(R.drawable.circle)
        private val fire =     intArrayOf(R.drawable.creature_fire,     R.drawable.creature_fire)
        private val water =    intArrayOf(R.drawable.creature_water,    R.drawable.creature_firewater,    R.drawable.creature_water)
        private val earth =    intArrayOf(R.drawable.creature_earth,    R .drawable.creature_fireearth,   R.drawable.creature_waterearth,    R.drawable.creature_earth)
        private val air =      intArrayOf(R.drawable.creature_air,      R.drawable.creature_fireair,      R.drawable.creature_waterair,      R.drawable.creature_earthair,      R.drawable.creature_air)
        private val plant =    intArrayOf(R.drawable.creature_plant,    R.drawable.creature_fireplant,    R.drawable.creature_waterplant,    R.drawable.creature_earthplant,    R.drawable.creature_plantair,    R.drawable.creature_plant)
        private val electric = intArrayOf(R.drawable.creature_electric, R.drawable.creature_fireelectric, R.drawable.creature_waterelectric, R.drawable.creature_electricearth, R.drawable.creature_electricair, R.drawable.creature_electricplant, R.drawable.creature_electric)
        private val drawables = arrayOf(any, fire, water, earth, air, plant, electric)
        @JvmStatic
        fun getCreatureDrawable(vararg types: Int): Int {
            types.sortDescending()
            Log.d("getCreatureDrawable", "${types[0]} ${types[1]}")
            when (types.count()) {
                0 -> return drawables[0][0]
                1 -> return drawables[0][types[0]]
                2 -> return drawables[types[0]][types[1]]
            }
            return drawables[0][0]
        }
    }

    object Types {
        private val drawables = intArrayOf(
                R.drawable.circle,
                R.drawable.ic_fire,
                R.drawable.ic_water,
                R.drawable.ic_earth,
                R.drawable.ic_air,
                R.drawable.ic_plant,
                R.drawable.ic_electricity
        )

        fun getTypesDrawable(vararg types: Int): Int { // Ignores type 2 for now.
//TODO: Make combo type drawables. Something like (fire/water)
            when (types.count()) {
                1 -> return drawables[types[0]]
                2 -> return drawables[types[0]]
            }
            return drawables[0]
        }

        fun getTypeFromRadioId(id: Int): String {
            if (id == R.id.fire_check_1 || id == R.id.fire_check_2) return me.nathanp.magiccreatures.model.Types.TYPE_FIRE
            if (id == R.id.water_check_1 || id == R.id.water_check_2) return me.nathanp.magiccreatures.model.Types.TYPE_WATER
            if (id == R.id.earth_check_1 || id == R.id.earth_check_2) return me.nathanp.magiccreatures.model.Types.TYPE_EARTH
            if (id == R.id.air_check_1 || id == R.id.air_check_2) return me.nathanp.magiccreatures.model.Types.TYPE_AIR
            if (id == R.id.plant_check_1 || id == R.id.plant_check_2) return me.nathanp.magiccreatures.model.Types.TYPE_PLANT
            return if (id == R.id.electricity_check_1 || id == R.id.electricity_check_2) me.nathanp.magiccreatures.model.Types.TYPE_ELECTRICITY else me.nathanp.magiccreatures.model.Types.TYPE_ANY
        }

        fun getRadioIdFromType1(type1: String): Int {
            if (type1 == me.nathanp.magiccreatures.model.Types.TYPE_FIRE) return R.id.fire_check_1
            if (type1 == me.nathanp.magiccreatures.model.Types.TYPE_WATER) return R.id.water_check_1
            if (type1 == me.nathanp.magiccreatures.model.Types.TYPE_EARTH) return R.id.earth_check_1
            if (type1 == me.nathanp.magiccreatures.model.Types.TYPE_AIR) return R.id.air_check_1
            if (type1 == me.nathanp.magiccreatures.model.Types.TYPE_PLANT) return R.id.plant_check_1
            return if (type1 == me.nathanp.magiccreatures.model.Types.TYPE_ELECTRICITY) R.id.electricity_check_1 else -1
        }

        fun getRadioIdFromType2(type2: String): Int {
            if (type2 == me.nathanp.magiccreatures.model.Types.TYPE_FIRE) return R.id.fire_check_2
            if (type2 == me.nathanp.magiccreatures.model.Types.TYPE_WATER) return R.id.water_check_2
            if (type2 == me.nathanp.magiccreatures.model.Types.TYPE_EARTH) return R.id.earth_check_2
            if (type2 == me.nathanp.magiccreatures.model.Types.TYPE_AIR) return R.id.air_check_2
            if (type2 == me.nathanp.magiccreatures.model.Types.TYPE_PLANT) return R.id.plant_check_2
            return if (type2 == me.nathanp.magiccreatures.model.Types.TYPE_ELECTRICITY) R.id.electricity_check_2 else -1
        }
    }
}