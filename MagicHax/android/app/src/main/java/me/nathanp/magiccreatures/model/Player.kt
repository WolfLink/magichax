package me.nathanp.magiccreatures.model

data class Player(
        var username: String = "",
        var currentGame: String = "",
        var isOnline: Boolean = false,
        var cards: List<String> = mutableListOf()
        )