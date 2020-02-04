package me.nathanp.magiccreatures.model

import android.util.Log
import java.io.Serializable

object Types {
    const val TYPE_ANY = "*"
    const val TYPE_FIRE = "F"
    const val TYPE_WATER = "W"
    const val TYPE_EARTH = "E"
    const val TYPE_AIR = "A"
    const val TYPE_PLANT = "P"
    const val TYPE_ELECTRICITY = "L"
    val TypeList = listOf( TYPE_ANY, TYPE_FIRE, TYPE_WATER, TYPE_EARTH, TYPE_AIR, TYPE_PLANT, TYPE_ELECTRICITY)
    val TypeMap = TypeList.mapIndexed { index, c -> c to index }.toMap()

    fun typeCompatible(a: Typed, b: Typed): Boolean {
        var matches = 0
        val wcCountA = a.getType().count { it == '*' }
        val wcCountB = b.getType().count { it == '*' }
        val sa = a.getType()
                .filter { it != '*' }
                .toList()
        val sb = b.getType()
                .filter { it != '*' }
                .toMutableList()
        for (c in sa) {
            if (c in sb) {
                matches += 1
                sb.remove(c)
            }
        }
        return matches + wcCountA + wcCountB >= minOf(a.getType().count(), b.getType().count())
    }

    open class Typed : Serializable {
        var type1: String = TYPE_ANY
            set(value) {
                Log.d("Types.Typed", "Setting value: $value")
                val f = value.toUpperCase().filter { "$it" in TypeMap.keys }
                if (f.count() == 1) {
                    field = f
                    Log.d("Types.Typed", "Set: $f")
                }
            }
        var type2: String = TYPE_ANY
            set(value) {
                Log.d("Types.Typed", "Setting value: $value")
                val f = value.toUpperCase().filter { "$it" in TypeMap.keys }
                if (f.count() == 1) field = f
            }
        fun setType(value: String) {
            Log.d("setType", "is used bro: $value")
            if (value.count() == 2) {
                type1 = "${value[0]}"
                type2 = "${value[1]}"
            }
        }
        fun getType(): String {
            Log.d("getType", "$type1$type2")
            return type1 + type2
        }
        fun getTypeAsSortedInts(): IntArray {
            return getType().map { TypeMap["$it"] }.sortedByDescending { it }.filterNotNull().toIntArray()
        }
    }
}
