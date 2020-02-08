package me.nathanp.magiccreatures.util

import me.nathanp.magiccreatures.R

object Resources {
    object Creatures {
        private val any =      intArrayOf(R.drawable.ic_grey_icon)
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
                R.drawable.ic_grey_icon,
                R.drawable.ic_fire_icon,
                R.drawable.ic_water_icon,
                R.drawable.ic_earth_icon,
                R.drawable.ic_air_icon,
                R.drawable.ic_plant_icon,
                R.drawable.ic_electric_icon
        )

        fun getTypeColor(type: String, secondary: Boolean = false): Int {
            return when (type) {
                me.nathanp.magiccreatures.model.Types.TYPE_FIRE -> if (!secondary) R.color.firePrimary else R.color.fireSecondary
                me.nathanp.magiccreatures.model.Types.TYPE_WATER -> if (!secondary) R.color.waterPrimary else R.color.waterSecondary
                me.nathanp.magiccreatures.model.Types.TYPE_EARTH -> if (!secondary) R.color.earthPrimary else R.color.earthSecondary
                me.nathanp.magiccreatures.model.Types.TYPE_AIR -> if (!secondary) R.color.airPrimary else R.color.airSecondary
                me.nathanp.magiccreatures.model.Types.TYPE_PLANT -> if (!secondary) R.color.plantPrimary else R.color.plantSecondary
                me.nathanp.magiccreatures.model.Types.TYPE_ELECTRICITY -> if (!secondary) R.color.electricPrimary else R.color.electricSecondary
                else -> if (!secondary) R.color.colorPrimary else R.color.colorAccent
            }
        }

        fun getTypesDrawable(vararg types: Int): Int { // Ignores type 2 for now.
//TODO: Make combo type drawables. Something like (fire/water)
            when (types.count()) {
                1 -> return drawables[types[0]]
                2 -> return drawables[types[0]]
            }
            return drawables[0]
        }

        fun getTypeFromRadioId(id: Int): String {
            return when (id) {
                R.id.fire_check_1, R.id.fire_check_2 -> me.nathanp.magiccreatures.model.Types.TYPE_FIRE
                R.id.water_check_1, R.id.water_check_2 -> me.nathanp.magiccreatures.model.Types.TYPE_WATER
                R.id.earth_check_1, R.id.earth_check_2 -> me.nathanp.magiccreatures.model.Types.TYPE_EARTH
                R.id.air_check_1, R.id.air_check_2 -> me.nathanp.magiccreatures.model.Types.TYPE_AIR
                R.id.plant_check_1, R.id.plant_check_2 -> me.nathanp.magiccreatures.model.Types.TYPE_PLANT
                R.id.electricity_check_1, R.id.electricity_check_2 -> me.nathanp.magiccreatures.model.Types.TYPE_ELECTRICITY
                else -> me.nathanp.magiccreatures.model.Types.TYPE_ANY
            }
        }

        fun getRadioIdFromType1(type1: String): Int {
            return when (type1) {
                me.nathanp.magiccreatures.model.Types.TYPE_FIRE -> R.id.fire_check_1
                me.nathanp.magiccreatures.model.Types.TYPE_WATER -> R.id.water_check_1
                me.nathanp.magiccreatures.model.Types.TYPE_EARTH -> R.id.earth_check_1
                me.nathanp.magiccreatures.model.Types.TYPE_AIR -> R.id.air_check_1
                me.nathanp.magiccreatures.model.Types.TYPE_PLANT -> R.id.plant_check_1
                me.nathanp.magiccreatures.model.Types.TYPE_ELECTRICITY -> R.id.electricity_check_1
                else -> -1
            }
        }

        fun getRadioIdFromType2(type2: String): Int {
            return when (type2) {
                me.nathanp.magiccreatures.model.Types.TYPE_FIRE -> R.id.fire_check_2
                me.nathanp.magiccreatures.model.Types.TYPE_WATER -> R.id.water_check_2
                me.nathanp.magiccreatures.model.Types.TYPE_EARTH -> R.id.earth_check_2
                me.nathanp.magiccreatures.model.Types.TYPE_AIR -> R.id.air_check_2
                me.nathanp.magiccreatures.model.Types.TYPE_PLANT -> R.id.plant_check_2
                me.nathanp.magiccreatures.model.Types.TYPE_ELECTRICITY -> R.id.electricity_check_2
                else -> -1
            }
        }
    }
}