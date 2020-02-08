package me.nathanp.magiccreatures.model

import me.nathanp.magiccreatures.util.Resources.Types.getTypesDrawable
import me.nathanp.magiccreatures.model.Types.Typed

data class Trait(
        var name: String = "",
        var cost: Int = 0,
        var description: String = ""
) : Typed() {
    val drawableId: Int
        get() = getTypesDrawable(*getTypeAsSortedInts())
}