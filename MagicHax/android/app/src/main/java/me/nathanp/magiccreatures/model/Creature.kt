package me.nathanp.magiccreatures.model

import me.nathanp.magiccreatures.util.Resources
import java.io.Serializable
import java.util.ArrayList

data class Creature(
        var name: String = "",
        var cards: ArrayList<String> = ArrayList(),
        var traits: ArrayList<String> = ArrayList(),
        var hp: Int = 0,
        var spd: Int = 0,
        var def: Int = 0,
        var atk: Int = 0,
        var mag: Int = 0
) : Types.Typed(), Serializable {

    val drawableId: Int
        get() = Resources.Creatures.getCreatureDrawable(*getTypeAsSortedInts())

    val statSummary: String
        get() = "hp: $hp\nspd: $spd, atk: $atk, def: $def, mag: $mag"

    init {
        this.traits = ArrayList()
        this.cards = ArrayList()
    }

    fun addCard(card: String) {
        cards.add(card)
    }

    fun removeCard(card: String) {
        cards.remove(card)
    }

    fun addTrait(trait: String): Boolean {
        return traits.add(trait)
    }

    fun removeTrait(trait: String): Boolean {
        return traits.remove(trait)
    }

    fun hasTrait(trait: String): Boolean {
        return traits.contains(trait)
    }
}
