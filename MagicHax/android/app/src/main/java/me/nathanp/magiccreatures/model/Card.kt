package me.nathanp.magiccreatures.model

class Card(
        var name: String = "",
        var cost: Int = 0,
        var description: String = ""
) : Types.Typed()
